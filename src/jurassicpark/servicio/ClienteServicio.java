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

    // Contador para generar IDs automáticamente
    private static int contadorClientes = 2010; // Empezamos desde 2010 ya que tenemos 10 clientes predefinidos en la
                                                // bdd

    /**
     * Constructor de la clase ClienteServicio
     */
    public ClienteServicio() {
        // Inicializar el DAO
        this.clienteDAO = new ClienteDAOMySQL();

    }

    /**
     * Actualiza el contador de IDs
     */
    private void actualizarContador() {
        try {
            // Obtener todos los clientes y actualizar contador si es necesario
            List<Cliente> clientes = clienteDAO.buscarTodos();
            for (Cliente cliente : clientes) {
                if (cliente.getId() > contadorClientes) {
                    contadorClientes = cliente.getId();
                }
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar contador de clientes: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo cliente con ID generado automáticamente
     *
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param email    Email del cliente
     * @param telefono Telefono del cliente
     * @return Cliente creado
     */
    public Cliente crearCliente(String nombre, String apellido, String email, String telefono) {
        actualizarContador();

        int id = ++contadorClientes; // Incrementamos el contador y asignamos el nuevo ID
        Cliente cliente = new Cliente(id, nombre, apellido, email, telefono);

        try {
            // Guardar en la base de datos
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
     */
    public Cliente crearCliente(int id, String nombre, String apellido, String email, String telefono) {
        actualizarContador();

        if (id > contadorClientes) {
            contadorClientes = id;
        }

        Cliente cliente = new Cliente(id, nombre, apellido, email, telefono);

        try {
            // Guardar en la base de datos
            return clienteDAO.guardar(cliente);
        } catch (Exception e) {
            System.out.println("Error al guardar cliente en BD: " + e.getMessage());
            return cliente;
        }
    }

    /**
     * Obtiene todos los clientes disponibles
     *
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        actualizarContador();

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

    // Getters y setters para el contador
    public static int getContadorClientes() {
        return contadorClientes;
    }

    public static void setContadorClientes(int nuevoContador) {
        contadorClientes = nuevoContador;
    }
}
