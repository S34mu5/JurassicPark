package jurassicpark.servicio;

import jurassicpark.connection.ConnectionJP;
import jurassicpark.modelo.gestion.Cliente;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servicio que gestiona las operaciones relacionadas con los clientes
 */
public class ClienteServicio {

    /**
     * Constructor de la clase ClienteServicio
     */
    public ClienteServicio() {

    }

    // Contador para generar IDs automáticamente
    private static int contadorClientes = 2010; // Empezamos desde 2010 ya que tenemos 10 clientes predefinidos

    // Lista para almacenar todos los clientes
    private List<Cliente> clientes = new ArrayList<>();

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
        int id = ++contadorClientes; // Incrementamos el contador y asignamos el nuevo ID
        Cliente cliente = new Cliente(id, nombre, apellido, email, telefono);
        clientes.add(cliente);
        return cliente;

    }

    /**
     * Crea un nuevo cliente con ID específico Esta versión se mantiene para
     * compatibilidad y propósitos de prueba
     *
     * @param id       ID del cliente
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param email    Email del cliente
     * @param telefono Telefono del cliente
     * @return Cliente creado
     */
    public Cliente crearCliente(int id, String nombre, String apellido, String email, String telefono) {
        // Actualizamos el contador si el ID proporcionado es mayor
        if (id > contadorClientes) {
            contadorClientes = id;
        }
        Cliente cliente = new Cliente(id, nombre, apellido, email, telefono);
        clientes.add(cliente);
        return cliente;
    }

    /**
     * Actualiza los datos de un cliente
     *
     * @param cliente  Cliente a actualizar
     * @param nombre   Nuevo nombre
     * @param apellido Nuevo apellido
     * @param email    Nuevo email
     * @param telefono Telefono del cliente
     * @return Cliente actualizado
     */
    public Cliente actualizarCliente(Cliente cliente, String nombre, String apellido, String email, String telefono) {
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        return cliente;
    }

    /**
     * Obtiene todos los clientes disponibles
     *
     * @return Lista de clientes
     */
    public List<Cliente> obtenerTodosLosClientes() {
        return new ArrayList<>(clientes); // Devolvemos una copia para evitar modificaciones externas
    }

    /**
     * Busca un cliente por su ID
     *
     * @param id ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarClientePorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Verifica si existen clientes registrados
     *
     * @return true si hay clientes registrados, false en caso contrario
     */
    public boolean hayClientesRegistrados() {
        return !clientes.isEmpty();
    }

    public static int getContadorClientes() {
        return contadorClientes;
    }

    public static void setContadorClientes(int contadorClientes) {
        ClienteServicio.contadorClientes = contadorClientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

}
