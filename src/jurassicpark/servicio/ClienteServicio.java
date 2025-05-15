package jurassicpark.servicio;

import jurassicpark.dao.IClienteDAO;
import jurassicpark.dao.ClienteDAOMySQL;
import jurassicpark.modelo.gestion.Cliente;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los clientes
 */
public class ClienteServicio {

    // DAO para acceso a datos
    private IClienteDAO clienteDAO;

    /**
     * Constructor de la clase ClienteServicio
     */
    public ClienteServicio() {
        // Inicializar el DAO
        this.clienteDAO = new ClienteDAOMySQL();
    }

    /**
     * Crea un nuevo cliente
     *
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param email    Email del cliente
     * @param telefono Telefono del cliente
     * @return Cliente creado
     */
    public Cliente crearCliente(String nombre, String apellido, String email, String telefono) {
        // Creamos cliente sin ID, el ID será asignado por la base de datos. Indicado
        // por Pedro.
        Cliente cliente = new Cliente(0, nombre, apellido, email, telefono);

        try {
            // Guardar en la base de datos, que asignará el ID automáticamente
            return clienteDAO.guardar(cliente);
        } catch (Exception e) {
            System.out.println("Error al guardar cliente en BD: " + e.getMessage());
            return cliente;
        }
    }

    /**
     * Crea un nuevo cliente con ID específico
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     *
     * @param id       ID del cliente
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param email    Email del cliente
     * @param telefono Telefono del cliente
     * @return Cliente creado
     * 
     *         public Cliente crearCliente(int id, String nombre, String apellido,
     *         String email, String telefono) {
     *         // Nota: El ID proporcionado será ignorado por la base de datos si
     *         usa
     *         // AUTO_INCREMENT
     *         Cliente cliente = new Cliente(id, nombre, apellido, email, telefono);
     * 
     *         try {
     *         // Guardar en la base de datos
     *         return clienteDAO.guardar(cliente);
     *         } catch (Exception e) {
     *         System.out.println("Error al guardar cliente en BD: " +
     *         e.getMessage());
     *         return cliente;
     *         }
     *         }
     */

    /**
     * Obtiene todos los clientes disponibles
     *
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        try {
            // Obtener de la base de datos
            return clienteDAO.buscarTodos();
        } catch (Exception e) {
            System.out.println("Error al obtener clientes de BD: " + e.getMessage());
            return List.of(); // Devolver lista vacía
        }
    }

    /**
     * Busca un cliente por su ID
     *
     * @param id ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarClientePorId(int id) {
        try {
            // Buscar en la base de datos
            return clienteDAO.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Error al buscar cliente en BD: " + e.getMessage());
            return null;
        }
    }
}
