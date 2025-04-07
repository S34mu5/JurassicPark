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
        String sql = "INSERT INTO cliente (id, nombre, apellido, email, telefono) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefono());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                return cliente;
            }
            throw new Exception("No se pudo guardar el cliente");
        }
    }

    @Override
    public Cliente buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM cliente WHERE id = ?";

        try (Connection conn = connectionJP.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("id"),
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