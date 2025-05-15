package jurassicpark.servicio;

import jurassicpark.dao.IVisitanteDAO;
import jurassicpark.dao.VisitanteDAOMySQL;
import jurassicpark.modelo.gestion.Visitante;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los visitantes
 */
public class VisitanteServicio {

    // DAO para acceso a datos
    private IVisitanteDAO visitanteDAO;

    // Contador para generar IDs automáticamente
    private static int contadorVisitantes = 6000; // Empezamos desde 6000

    /**
     * Constructor de la clase VisitanteServicio
     */
    public VisitanteServicio() {
        this.visitanteDAO = new VisitanteDAOMySQL();
        actualizarContador();
    }

    /**
     * Actualiza el contador de IDs
     */
    private void actualizarContador() {
        try {
            // Obtener todos los visitantes y actualizar contador si es necesario
            List<Visitante> visitantes = visitanteDAO.buscarTodos();
            for (Visitante visitante : visitantes) {
                if (visitante.getId() > contadorVisitantes) {
                    contadorVisitantes = visitante.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar contador de visitantes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo visitante con ID generado automáticamente
     * 
     * @param nombre   Nombre del visitante
     * @param apellido Apellido del visitante
     * @param edad     Edad del visitante
     * @return Visitante creado
     */
    public Visitante crearVisitante(String nombre, String apellido, int edad) {
        int id = ++contadorVisitantes;
        Visitante visitante = new Visitante(id, nombre, apellido, edad);

        try {
            // Guardar en la base de datos
            return visitanteDAO.guardar(visitante);
        } catch (Exception e) {
            System.out.println("Error al guardar visitante en BD: " + e.getMessage());
            return visitante;
        }
    }

    /**
     * Busca un visitante por su ID
     * 
     * @param id ID del visitante a buscar
     * @return Visitante encontrado o null si no existe
     */
    public Visitante buscarVisitantePorId(int id) {
        try {
            return visitanteDAO.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Error al buscar visitante por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todos los visitantes registrados
     * 
     * @return Lista de visitantes
     */
    public List<Visitante> obtenerTodosLosVisitantes() {
        try {
            return visitanteDAO.buscarTodos();
        } catch (Exception e) {
            System.out.println("Error al obtener todos los visitantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}