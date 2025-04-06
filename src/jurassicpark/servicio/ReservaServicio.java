package jurassicpark.servicio;

import jurassicpark.modelo.gestion.Cliente;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Pago;
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

    public ReservaServicio() {

    }

    // Contador para generar IDs automáticamente
    private static int contadorReservas = 1000; // Empezamos desde 1000

    // Lista para almacenar todas las reservas
    private List<Reserva> reservas = new ArrayList<>();

    /**
     * Crea una nueva reserva con ID generado automáticamente
     * 
     * @param cliente     Cliente que realiza la reserva
     * @param fechaVisita Fecha de la visita
     * @return Reserva creada
     */
    public Reserva crearReserva(Cliente cliente, Date fechaVisita) {
        int id = ++contadorReservas; // Incrementamos el contador y asignamos el nuevo ID
        Reserva reserva = new Reserva(id, cliente, fechaVisita);
        reservas.add(reserva);
        return reserva;
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
        // Actualizamos el contador si el ID proporcionado es mayor
        if (id > contadorReservas) {
            contadorReservas = id;
        }
        Reserva reserva = new Reserva(id, cliente, fechaVisita);
        reservas.add(reserva);
        return reserva;
    }

    /**
     * Actualiza el estado de una reserva basándose en el pago
     * 
     * @param reserva Reserva a actualizar
     * @param pago    Pago asociado a la reserva
     * @return Reserva actualizada
     */
    public Reserva actualizarEstadoReserva(Reserva reserva, Pago pago) {
        if (pago.getEstado() == Pago.EstadoPago.CONFIRMADO) {
            reserva.setEstado(Reserva.EstadoReserva.CONFIRMADA);
        } else if (pago.getEstado() == Pago.EstadoPago.CANCELADO) {
            reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
        }
        reserva.setPago(pago);
        return reserva;
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
        return this.reservas;
    }

    /**
     * Busca una reserva por su ID
     * 
     * @param id ID de la reserva a buscar
     * @return Reserva encontrada o null si no existe
     */
    public Reserva buscarReservaPorId(int id) {
        for (Reserva reserva : reservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    /**
     * Obtiene todas las reservas de un cliente específico
     * 
     * @param cliente Cliente del que se quieren obtener las reservas
     * @return Lista de reservas del cliente
     */
    public List<Reserva> obtenerReservasPorCliente(Cliente cliente) {
        List<Reserva> reservasCliente = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().getId() == cliente.getId()) {
                reservasCliente.add(reserva);
            }
        }
        return reservasCliente;
    }

    /**
     * Verifica si existen reservas registradas
     * 
     * @return true si hay reservas registradas, false en caso contrario
     */
    public boolean hayReservasRegistradas() {
        return !reservas.isEmpty();
    }
}