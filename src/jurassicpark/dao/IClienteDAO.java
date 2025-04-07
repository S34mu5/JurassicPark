package jurassicpark.dao;

import jurassicpark.modelo.gestion.Cliente;
import java.util.List;

/**
 * Interfaz para el acceso a datos de Cliente
 */
public interface IClienteDAO {

    /**
     * Guarda un cliente en la base de datos
     * 
     * @param cliente Cliente a guardar
     * @return Cliente guardado
     */
    Cliente guardar(Cliente cliente) throws Exception;

    /**
     * Busca un cliente por su ID
     * 
     * @param id ID del cliente
     * @return Cliente encontrado o null
     */
    Cliente buscarPorId(int id) throws Exception;

    /**
     * Obtiene todos los clientes
     * 
     * @return Lista de clientes
     */
    List<Cliente> buscarTodos() throws Exception;
}