package jurassicpark.dao;

import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.Reserva;
import java.util.List;

/**
 * Interfaz para el acceso a datos de Entrada
 */
public interface IEntradaDAO {

    /**
     * Guarda una entrada en la base de datos
     * 
     * @param entrada Entrada a guardar
     * @return Entrada guardada
     */
    Entrada guardar(Entrada entrada) throws Exception;

    /**
     * Busca una entrada por su ID
     * 
     * @param id ID de la entrada
     * @return Entrada encontrada o null
     */
    Entrada buscarPorId(int id) throws Exception;

    /**
     * Obtiene todas las entradas
     * 
     * @return Lista de entradas
     */
    List<Entrada> buscarTodas() throws Exception;

    /**
     * Obtiene todas las entradas de una reserva
     * 
     * @param reservaId ID de la reserva
     * @return Lista de entradas de la reserva
     */
    List<Entrada> buscarPorReserva(int reservaId) throws Exception;
}