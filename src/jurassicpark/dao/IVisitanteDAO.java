package jurassicpark.dao;

import jurassicpark.modelo.gestion.Visitante;
import java.util.List;

/**
 * Interfaz para el acceso a datos de Visitante
 */
public interface IVisitanteDAO {

    /**
     * Guarda un visitante en la base de datos
     * 
     * @param visitante Visitante a guardar
     * @return Visitante guardado
     */
    Visitante guardar(Visitante visitante) throws Exception;

    /**
     * Busca un visitante por su ID
     * 
     * @param id ID del visitante
     * @return Visitante encontrado o null
     */
    Visitante buscarPorId(int id) throws Exception;

    /**
     * Obtiene todos los visitantes
     * 
     * @return Lista de visitantes
     */
    List<Visitante> buscarTodos() throws Exception;
}