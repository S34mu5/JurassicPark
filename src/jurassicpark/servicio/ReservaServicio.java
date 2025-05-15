package jurassicpark.servicio;

import jurassicpark.dao.IReservaDAO;
import jurassicpark.dao.ReservaDAOMySQL;
import jurassicpark.modelo.gestion.Cliente;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.Visitante;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Servicio que gestiona las operaciones relacionadas con las reservas
 */
public class ReservaServicio {

    // DAO para acceso a datos
    private IReservaDAO reservaDAO;

    // Contador para generar IDs automáticamente
    private static int contadorReservas = 1000; // Empezamos desde 1000

    /**
     * Constructor de la clase ReservaServicio
     */
    public ReservaServicio() {
        // Inicializar el DAO
        this.reservaDAO = new ReservaDAOMySQL();
        actualizarContador();
    }

    /**
     * Actualiza el contador de IDs
     */
    private void actualizarContador() {
        try {
            // Obtener todas las reservas y actualizar contador si es necesario
            List<Reserva> reservas = reservaDAO.buscarTodas();
            for (Reserva reserva : reservas) {
                if (reserva.getId() > contadorReservas) {
                    contadorReservas = reserva.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar contador de reservas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Crea una nueva reserva con ID generado automáticamente
     * 
     * @param cliente     Cliente que realiza la reserva
     * @param fechaVisita Fecha de la visita
     * @return Reserva creada
     */
    public Reserva crearReserva(Cliente cliente, Date fechaVisita) {
        actualizarContador();

        int id = ++contadorReservas; // Incrementamos el contador y asignamos el nuevo ID
        Reserva reserva = new Reserva(id, cliente, fechaVisita);

        try {
            // Guardar en la base de datos
            return reservaDAO.guardar(reserva);
        } catch (Exception e) {
            System.out.println("Error al guardar reserva en BD: " + e.getMessage());
            return reserva;
        }
    }

    /**
     * Crea una nueva reserva con ID específico
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     * 
     * @param id          ID de la reserva
     * @param cliente     Cliente que realiza la reserva
     * @param fechaVisita Fecha de la visita
     * @return Reserva creada
     */
    public Reserva crearReserva(int id, Cliente cliente, Date fechaVisita) {
        actualizarContador();

        if (id > contadorReservas) {
            contadorReservas = id;
        }

        Reserva reserva = new Reserva(id, cliente, fechaVisita);

        try {
            // Guardar en la base de datos
            return reservaDAO.guardar(reserva);
        } catch (Exception e) {
            System.out.println("Error al guardar reserva en BD: " + e.getMessage());
            return reserva;
        }
    }

    /**
     * Asigna un visitante a una entrada específica
     * 
     * @param reserva   Reserva a la que pertenece la entrada
     * @param entrada   Entrada a asignar
     * @param visitante Visitante a asignar
     * @return true si se asignó correctamente
     */
    public boolean asignarVisitante(Reserva reserva, Entrada entrada, Visitante visitante) {
        if (reserva.getEntradas().contains(entrada)) {
            entrada.asignarVisitante(visitante);
            return true;
        }
        return false;
    }

    /**
     * Obtiene todas las reservas disponibles
     * 
     * @return Lista de reservas
     */
    public List<Reserva> obtenerTodasLasReservas() {
        try {
            // Obtener de la base de datos
            return reservaDAO.buscarTodas();
        } catch (Exception e) {
            System.out.println("Error al obtener reservas de BD: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca una reserva por su ID
     * 
     * @param id ID de la reserva a buscar
     * @return Reserva encontrada o null si no existe
     */
    public Reserva buscarReservaPorId(int id) {
        try {
            // Buscar en la base de datos
            return reservaDAO.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Error al buscar reserva en BD: " + e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todas las reservas de un cliente específico
     * 
     * @param cliente Cliente del que se quieren obtener las reservas
     * @return Lista de reservas del cliente
     */
    public List<Reserva> obtenerReservasPorCliente(Cliente cliente) {
        try {
            // Obtener de la base de datos
            return reservaDAO.buscarPorCliente(cliente.getId());
        } catch (Exception e) {
            System.out.println("Error al obtener reservas por cliente de BD: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Verifica si existen reservas registradas
     * 
     * @return true si hay reservas registradas, false en caso contrario
     */
    public boolean hayReservasRegistradas() {
        List<Reserva> reservas = obtenerTodasLasReservas();
        return !reservas.isEmpty();
    }

    /**
     * Actualiza una reserva existente en la base de datos
     * Útil después de modificar una reserva (ej. añadir entradas o visitantes)
     * 
     * @param reserva Reserva a actualizar
     * @return Reserva actualizada
     */
    public Reserva actualizarReserva(Reserva reserva) throws Exception {
        try {
            // Guardar en la base de datos
            return reservaDAO.guardar(reserva);
        } catch (Exception e) {
            System.out.println("Error al actualizar reserva en BD: " + e.getMessage());
            throw e; // Re-lanzar la excepción para que el controlador pueda manejarla
        }
    }
}