package jurassicpark.dao;

import jurassicpark.connection.ConnectionJP;
import jurassicpark.modelo.gestion.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

/**
 * Implementación de IClienteDAO para MySQL
 */
public class ClienteDAOMySQL implements IClienteDAO {

    private ConnectionJP connectionJP;

    /**
     * Constructor de la clase ClienteDAOMySQL
     */
    public ClienteDAOMySQL() {
        this.connectionJP = new ConnectionJP();
    }

    @Override
    public Cliente guardar(Cliente cliente) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        // Comentado para probar si funciona sin generatedKeys
        // ResultSet generatedKeys = null;

        try {
            conn = connectionJP.getConnection();

            // INSERT nuevo (sin idcliente, ya que es AUTO_INCREMENT)
            String insertSql = "INSERT INTO cliente (nombre, apellido, email, telefono) VALUES (?, ?, ?, ?)";
            // Esto es clave. Le indico a JDBC que, tras ejecutar la instrucción,
            // me devuelva cualquier columna con valor generado automáticamente
            // (la clave primaria auto‐incremental que antes generaba desde Java y cuya
            // creación delego ahora en MySQL).

            // Comentado para probar si funciona sin generatedKeys
            // stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            // Versión sin retorno de claves generadas
            stmt = conn.prepareStatement(insertSql);

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());

            stmt.executeUpdate();

         

            return cliente;
        } finally {
         
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM cliente WHERE idcliente = ?";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("idcliente"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("telefono"));
                    return cliente;
                }
                return null;
            }
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("idcliente"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"));
                clientes.add(cliente);
            }
            return clientes;
        }
    }
}