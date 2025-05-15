package jurassicpark.dao;

import jurassicpark.modelo.gestion.Reserva;
import java.util.List;

/**
 * Interfaz para el acceso a datos de Reserva
 */
public interface IReservaDAO {

    /**
     * Guarda una reserva en la base de datos
     * 
     * @param reserva Reserva a guardar
     * @return Reserva guardada
     */
    Reserva guardar(Reserva reserva) throws Exception;

    /**
     * Busca una reserva por su ID
     * 
     * @param id ID de la reserva
     * @return Reserva encontrada o null
     */
    Reserva buscarPorId(int id) throws Exception;

    /**
     * Obtiene todas las reservas
     * 
     * @return Lista de reservas
     */
    List<Reserva> buscarTodas() throws Exception;

    /**
     * Obtiene todas las reservas de un cliente
     * 
     * @param clienteId ID del cliente
     * @return Lista de reservas del cliente
     */
    List<Reserva> buscarPorCliente(int clienteId) throws Exception;
}