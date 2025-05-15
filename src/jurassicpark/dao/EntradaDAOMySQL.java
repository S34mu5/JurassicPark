package jurassicpark.dao;

import jurassicpark.connection.ConnectionJP;
import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Visitante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementación de IEntradaDAO para MySQL
 */
public class EntradaDAOMySQL implements IEntradaDAO {

    private ConnectionJP connectionJP;
    private IReservaDAO reservaDAO;

    /**
     * Constructor de la clase EntradaDAOMySQL
     */
    public EntradaDAOMySQL() {
        this.connectionJP = new ConnectionJP();
        this.reservaDAO = new ReservaDAOMySQL();
    }

    @Override
    public Entrada guardar(Entrada entrada) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            // Verificar si la entrada ya existe
            String checkSql = "SELECT COUNT(*) FROM entrada WHERE identrada = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, entrada.getId());

            ResultSet rs = stmt.executeQuery();
            boolean existe = false;
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            rs.close();
            stmt.close();

            // Según exista o no, hacemos INSERT o UPDATE
            if (existe) {
                // Obtener el estado actual de la entrada para no cambiar datos innecesariamente
                String getEntrySql = "SELECT visitante_id, utilizada FROM entrada WHERE identrada = ?";
                stmt = conn.prepareStatement(getEntrySql);
                stmt.setInt(1, entrada.getId());
                rs = stmt.executeQuery();

                Integer currentVisitanteId = null;
                boolean currentUtilizada = false;

                if (rs.next()) {
                    if (!rs.wasNull()) {
                        currentVisitanteId = rs.getInt("visitante_id");
                    }
                    currentUtilizada = rs.getBoolean("utilizada");
                }
                rs.close();
                stmt.close();

                // Determinar si ha cambiado el visitante
                boolean visitanteChanged = (entrada.getVisitante() != null
                        && (currentVisitanteId == null || !currentVisitanteId.equals(entrada.getVisitante().getId())))
                        ||
                        (entrada.getVisitante() == null && currentVisitanteId != null);

                // Determinar si ha cambiado utilizada
                boolean utilizadaChanged = currentUtilizada != entrada.isUtilizada();

                // Solo actualizamos lo que ha cambiado
                if (visitanteChanged) {
                    // UPDATE para asignar visitante
                    String updateSql = "UPDATE entrada SET visitante_id = ? WHERE identrada = ?";
                    stmt = conn.prepareStatement(updateSql);

                    if (entrada.getVisitante() != null) {
                        stmt.setInt(1, entrada.getVisitante().getId());
                    } else {
                        stmt.setNull(1, java.sql.Types.INTEGER);
                    }
                    stmt.setInt(2, entrada.getId());

                    stmt.executeUpdate();
                    stmt.close();
                }

                if (utilizadaChanged) {
                    // UPDATE para marcar como utilizada (esto se hará desde la opción 6 del menú)
                    String updateSql = "UPDATE entrada SET utilizada = ? WHERE identrada = ?";
                    stmt = conn.prepareStatement(updateSql);

                    stmt.setBoolean(1, entrada.isUtilizada());
                    stmt.setInt(2, entrada.getId());

                    stmt.executeUpdate();
                }
            } else {
                // INSERT nuevo
                String insertSql = "INSERT INTO entrada (identrada, reserva_id, visitante_id, precio, fecha_emision, utilizada) VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertSql);

                stmt.setInt(1, entrada.getId());
                stmt.setInt(2, entrada.getReserva().getId());
                // visitante puede ser null
                if (entrada.getVisitante() != null) {
                    stmt.setInt(3, entrada.getVisitante().getId());
                } else {
                    stmt.setNull(3, java.sql.Types.INTEGER);
                }
                stmt.setDouble(4, entrada.getPrecio());
                stmt.setTimestamp(5, new Timestamp(entrada.getFechaEmision().getTime()));
                stmt.setBoolean(6, entrada.isUtilizada());

                stmt.executeUpdate();
            }

            return entrada;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    /* ignorar */ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    /* ignorar */ }
            }
        }
    }

    @Override
    public Entrada buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM entrada WHERE identrada = ?";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int reservaId = rs.getInt("reserva_id");
                    Reserva reserva = reservaDAO.buscarPorId(reservaId);

                    if (reserva == null) {
                        throw new Exception("No se encontró la reserva asociada a la entrada");
                    }

                    Entrada entrada = new Entrada(
                            rs.getInt("identrada"),
                            reserva,
                            rs.getDouble("precio"));

                    if (rs.getBoolean("utilizada")) {
                        entrada.marcarComoUtilizada();
                    }

                    // Visitante se asignará más tarde si corresponde

                    return entrada;
                }
                return null;
            }
        }
    }

    @Override
    public List<Entrada> buscarTodas() throws Exception {
        List<Entrada> entradas = new ArrayList<>();
        String sql = "SELECT * FROM entrada";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int reservaId = rs.getInt("reserva_id");
                Reserva reserva = reservaDAO.buscarPorId(reservaId);

                if (reserva == null) {
                    continue; // Saltamos esta entrada si no encontramos la reserva
                }

                Entrada entrada = new Entrada(
                        rs.getInt("identrada"),
                        reserva,
                        rs.getDouble("precio"));

                if (rs.getBoolean("utilizada")) {
                    entrada.marcarComoUtilizada();
                }

                // Visitante se asignará más tarde si corresponde

                entradas.add(entrada);
            }
            return entradas;
        }
    }

    @Override
    public List<Entrada> buscarPorReserva(int reservaId) throws Exception {
        List<Entrada> entradas = new ArrayList<>();
        String sql = "SELECT * FROM entrada WHERE reserva_id = ?";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservaId);

            try (ResultSet rs = stmt.executeQuery()) {
                Reserva reserva = reservaDAO.buscarPorId(reservaId);

                if (reserva == null) {
                    throw new Exception("No se encontró la reserva");
                }

                while (rs.next()) {
                    Entrada entrada = new Entrada(
                            rs.getInt("identrada"),
                            reserva,
                            rs.getDouble("precio"));

                    if (rs.getBoolean("utilizada")) {
                        entrada.marcarComoUtilizada();
                    }

                    // Visitante se asignará más tarde si corresponde

                    entradas.add(entrada);
                }
                return entradas;
            }
        }
    }
}