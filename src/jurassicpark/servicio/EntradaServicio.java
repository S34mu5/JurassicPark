package jurassicpark.servicio;

import jurassicpark.dao.IEntradaDAO;
import jurassicpark.dao.EntradaDAOMySQL;
import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Visitante;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con las entradas
 */
public class EntradaServicio {

    // DAO para acceso a datos
    private IEntradaDAO entradaDAO;

    // Contador para generar IDs automáticamente
    private static int contadorEntradas = 5000; // Empezamos desde 5000

    /**
     * Constructor de la clase EntradaServicio
     */
    public EntradaServicio() {
        this.entradaDAO = new EntradaDAOMySQL();
        actualizarContador();
    }

    /**
     * Actualiza el contador de IDs
     */
    private void actualizarContador() {
        try {
            // Obtener todas las entradas y actualizar contador si es necesario
            List<Entrada> entradas = entradaDAO.buscarTodas();
            for (Entrada entrada : entradas) {
                if (entrada.getId() > contadorEntradas) {
                    contadorEntradas = entrada.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar contador de entradas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Genera entradas para una reserva
     * 
     * @param reserva        Reserva a la que pertenecen las entradas
     * @param cantidad       Cantidad de entradas a generar
     * @param precioUnitario Precio unitario de cada entrada
     * @return Lista de entradas generadas
     */
    public List<Entrada> generarEntradas(Reserva reserva, int cantidad, double precioUnitario) {
        actualizarContador();

        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int entradaId = ++contadorEntradas; // Incrementamos el contador y asignamos el nuevo ID
            Entrada entrada = new Entrada(entradaId, reserva, precioUnitario);
            reserva.agregarEntrada(entrada);

            try {
                // Guardar en la base de datos
                entradaDAO.guardar(entrada);
                entradas.add(entrada);
            } catch (Exception e) {
                System.out.println("Error al guardar entrada en BD: " + e.getMessage());
                // Seguimos intentando con las demás entradas
            }
        }
        return entradas;
    }

    /**
     * Genera entradas para una reserva con IDs específicos
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     * 
     * @param reserva        Reserva a la que pertenecen las entradas
     * @param cantidad       Cantidad de entradas a generar
     * @param precioUnitario Precio unitario de cada entrada
     * @param idInicial      ID inicial para las entradas
     * @return Lista de entradas generadas
     */
    public List<Entrada> generarEntradas(Reserva reserva, int cantidad, double precioUnitario, int idInicial) {
        actualizarContador();

        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int entradaId = idInicial + i;

            if (entradaId > contadorEntradas) {
                contadorEntradas = entradaId;
            }

            Entrada entrada = new Entrada(entradaId, reserva, precioUnitario);
            reserva.agregarEntrada(entrada);

            try {
                // Guardar en la base de datos
                entradaDAO.guardar(entrada);
                entradas.add(entrada);
            } catch (Exception e) {
                System.out.println("Error al guardar entrada en BD: " + e.getMessage());
                // Seguimos intentando con las demás entradas
            }
        }
        return entradas;
    }

    /**
     * Calcular el precio total de una lista de entradas
     * 
     * @param entradas Lista de entradas
     * @return Precio total
     */
    public double calcularPrecioTotal(List<Entrada> entradas) {
        double total = 0;
        for (Entrada entrada : entradas) {
            total += entrada.getPrecio();
        }
        return total;
    }

    /**
     * Marca una entrada como utilizada
     * 
     * @param entrada Entrada a marcar
     * @return true si se pudo marcar correctamente
     */
    public boolean marcarEntradaComoUtilizada(Entrada entrada) {
        if (!entrada.isUtilizada()) {
            entrada.marcarComoUtilizada();

            try {
                // Actualizar en la base de datos
                entradaDAO.guardar(entrada);
                return true;
            } catch (Exception e) {
                System.out.println("Error al actualizar entrada en BD: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    /**
     * Asigna visitantes a las entradas
     * 
     * @param reserva    Reserva a la que pertenecen las entradas
     * @param entradas   Lista de entradas
     * @param visitantes Lista de visitantes
     */
    public void asignarVisitantes(Reserva reserva, List<Entrada> entradas, List<Visitante> visitantes) {
        int minSize = Math.min(entradas.size(), visitantes.size());
        for (int i = 0; i < minSize; i++) {
            Entrada entrada = entradas.get(i);
            entrada.asignarVisitante(visitantes.get(i));

            try {
                // Actualizar en la base de datos
                entradaDAO.guardar(entrada);
            } catch (Exception e) {
                System.out.println("Error al actualizar entrada en BD: " + e.getMessage());
                // Seguimos intentando con las demás entradas
            }
        }
    }

    /**
     * Obtiene todas las entradas
     * 
     * @return Lista de todas las entradas
     */
    public List<Entrada> obtenerTodasLasEntradas() {
        try {
            return entradaDAO.buscarTodas();
        } catch (Exception e) {
            System.out.println("Error al obtener entradas de BD: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene las entradas de una reserva
     * 
     * @param reserva Reserva de la que se quieren obtener las entradas
     * @return Lista de entradas de la reserva
     */
    public List<Entrada> obtenerEntradasPorReserva(Reserva reserva) {
        try {
            return entradaDAO.buscarPorReserva(reserva.getId());
        } catch (Exception e) {
            System.out.println("Error al obtener entradas por reserva de BD: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}