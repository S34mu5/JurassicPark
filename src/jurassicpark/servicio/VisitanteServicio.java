package jurassicpark.servicio;

import jurassicpark.modelo.gestion.Visitante;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los visitantes
 */
public class VisitanteServicio {

    // Contador para generar IDs automáticamente
    private static int contadorVisitantes = 6000; // Empezamos desde 6000

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
        return new Visitante(id, nombre, apellido, edad);
    }






}