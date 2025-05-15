package jurassicpark.dao;

import jurassicpark.connection.ConnectionJP;
import jurassicpark.modelo.gestion.Cliente;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Entrada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

/**
 * Implementación de IReservaDAO para MySQL
 */
public class ReservaDAOMySQL implements IReservaDAO {

    private ConnectionJP connectionJP;
    private IClienteDAO clienteDAO;

    /**
     * Constructor de la clase ReservaDAOMySQL
     */
    public ReservaDAOMySQL() {
        this.connectionJP = new ConnectionJP();
        this.clienteDAO = new ClienteDAOMySQL();
    }

    @Override
    public Reserva guardar(Reserva reserva) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            // Primero verificamos si la reserva ya existe
            String checkSql = "SELECT COUNT(*) FROM reserva WHERE idreserva = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, reserva.getId());

            ResultSet rs = stmt.executeQuery();
            boolean existe = false;
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            rs.close();
            stmt.close();

            // Solo insertamos si no existe
            if (!existe) {
                // INSERT nuevo
                String insertSql = "INSERT INTO reserva (idreserva, cliente_id, fecha_reserva, fecha_visita, estado) VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertSql);

                stmt.setInt(1, reserva.getId());
                stmt.setInt(2, reserva.getCliente().getId());
                stmt.setTimestamp(3, new Timestamp(reserva.getFechaReserva().getTime()));
                stmt.setTimestamp(4, new Timestamp(reserva.getFechaVisita().getTime()));
                stmt.setString(5, reserva.getEstado().name());

                stmt.executeUpdate();
            }

            return reserva;
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
    public Reserva buscarPorId(int id) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            String sql = "SELECT * FROM reserva WHERE idreserva = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteDAO.buscarPorId(clienteId);

                if (cliente == null) {
                    throw new Exception("No se encontró el cliente asociado a la reserva");
                }

                Date fechaVisita = new Date(rs.getTimestamp("fecha_visita").getTime());

                Reserva reserva = new Reserva(
                        rs.getInt("idreserva"),
                        cliente,
                        fechaVisita);

                // Establecer la fecha de reserva desde la BD
                Date fechaReserva = new Date(rs.getTimestamp("fecha_reserva").getTime());
                reserva.setEstado(Reserva.EstadoReserva.valueOf(rs.getString("estado")));

                // Cargar las entradas asociadas a esta reserva
                cargarEntradasParaReserva(conn, reserva);

                return reserva;
            }
            return null;
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
    public List<Reserva> buscarTodas() throws Exception {
        List<Reserva> reservas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            // Primero obtenemos todas las reservas
            String sql = "SELECT * FROM reserva";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = clienteDAO.buscarPorId(clienteId);

                if (cliente == null) {
                    continue; // Saltamos esta reserva si no encontramos el cliente
                }

                Date fechaVisita = new Date(rs.getTimestamp("fecha_visita").getTime());

                Reserva reserva = new Reserva(
                        rs.getInt("idreserva"),
                        cliente,
                        fechaVisita);

                // Establecer la fecha de reserva desde la BD
                Date fechaReserva = new Date(rs.getTimestamp("fecha_reserva").getTime());
                reserva.setEstado(Reserva.EstadoReserva.valueOf(rs.getString("estado")));

                // Ahora cargamos las entradas de esta reserva
                int reservaId = reserva.getId();
                cargarEntradasParaReserva(conn, reserva);

                reservas.add(reserva);
            }
            rs.close();

            return reservas;
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

    /**
     * Carga las entradas para una reserva específica desde la base de datos
     * 
     * @param conn    Conexión a la base de datos
     * @param reserva Reserva a la que se cargarán las entradas
     * @throws Exception Si ocurre un error al cargar las entradas
     */
    private void cargarEntradasParaReserva(Connection conn, Reserva reserva) throws Exception {
        String sql = "SELECT * FROM entrada WHERE reserva_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Entrada entrada = new Entrada(
                            rs.getInt("identrada"),
                            reserva,
                            rs.getDouble("precio"));

                    if (rs.getBoolean("utilizada")) {
                        entrada.marcarComoUtilizada();
                    }

                    // Aquí podrías cargar el visitante si existe
                    int visitanteId = rs.getInt("visitante_id");
                    if (!rs.wasNull()) {
                        // TODO: Cargar el visitante desde la BD si es necesario
                    }

                    // Agregamos la entrada a la reserva
                    reserva.agregarEntrada(entrada);
                }
            }
        }
    }

    @Override
    public List<Reserva> buscarPorCliente(int clienteId) throws Exception {
        List<Reserva> reservas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            String sql = "SELECT * FROM reserva WHERE cliente_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clienteId);

            ResultSet rs = stmt.executeQuery();
            Cliente cliente = clienteDAO.buscarPorId(clienteId);

            if (cliente == null) {
                throw new Exception("No se encontró el cliente");
            }

            while (rs.next()) {
                Date fechaVisita = new Date(rs.getTimestamp("fecha_visita").getTime());

                Reserva reserva = new Reserva(
                        rs.getInt("idreserva"),
                        cliente,
                        fechaVisita);

                // Establecer la fecha de reserva desde la BD
                Date fechaReserva = new Date(rs.getTimestamp("fecha_reserva").getTime());
                reserva.setEstado(Reserva.EstadoReserva.valueOf(rs.getString("estado")));

                // Cargar entradas
                cargarEntradasParaReserva(conn, reserva);

                reservas.add(reserva);
            }
            rs.close();

            return reservas;
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
}