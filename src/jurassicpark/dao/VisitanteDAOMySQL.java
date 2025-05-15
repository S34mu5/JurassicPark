package jurassicpark.dao;

import jurassicpark.connection.ConnectionJP;
import jurassicpark.modelo.gestion.Visitante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de IVisitanteDAO para MySQL
 */
public class VisitanteDAOMySQL implements IVisitanteDAO {

    private ConnectionJP connectionJP;

    /**
     * Constructor de la clase VisitanteDAOMySQL
     */
    public VisitanteDAOMySQL() {
        this.connectionJP = new ConnectionJP();
    }

    @Override
    public Visitante guardar(Visitante visitante) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = connectionJP.getConnection();

            // Primero verificamos si el visitante ya existe
            String checkSql = "SELECT COUNT(*) FROM visitante WHERE idvisitante = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, visitante.getId());

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
                String insertSql = "INSERT INTO visitante (idvisitante, nombre, apellido, edad) VALUES (?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertSql);

                stmt.setInt(1, visitante.getId());
                stmt.setString(2, visitante.getNombre());
                stmt.setString(3, visitante.getApellido());
                stmt.setInt(4, visitante.getEdad());

                stmt.executeUpdate();
            }

            return visitante;
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
    public Visitante buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM visitante WHERE idvisitante = ?";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Visitante visitante = new Visitante(
                            rs.getInt("idvisitante"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getInt("edad"));

                    return visitante;
                }
                return null;
            }
        }
    }

    @Override
    public List<Visitante> buscarTodos() throws Exception {
        List<Visitante> visitantes = new ArrayList<>();
        String sql = "SELECT * FROM visitante";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Visitante visitante = new Visitante(
                        rs.getInt("idvisitante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"));

                visitantes.add(visitante);
            }
            return visitantes;
        }
    }
}