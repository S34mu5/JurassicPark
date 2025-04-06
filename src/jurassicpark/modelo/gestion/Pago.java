package jurassicpark.modelo.gestion;

import java.util.Date;

/**
 * Clase que representa un pago de una reserva.
 */
public class Pago {
    public enum EstadoPago {
        PENDIENTE, CONFIRMADO, CANCELADO
    }

    private int id;
    private Reserva reserva;
    private double total;
    private Date fechaPago;
    private EstadoPago estado;

    /**
     * Constructor de la clase Pago
     * 
     * @param id      Identificador único del pago
     * @param reserva Reserva asociada al pago
     * @param total   Total del pago
     */
    public Pago(int id, Reserva reserva, double total) {
        this.id = id;
        this.reserva = reserva;
        this.total = total;
        this.estado = EstadoPago.PENDIENTE;
    }

    /**
     * Confirma el pago
     */
    public void confirmarPago() {
        this.fechaPago = new Date(); // Fecha actual
        this.estado = EstadoPago.CONFIRMADO;
    }

    /**
     * Cancela el pago
     */
    public void cancelarPago() {
        this.estado = EstadoPago.CANCELADO;
    }

    // Getters y setters
    /**
     * Obtiene el identificador del pago
     * 
     * @return Identificador único del pago
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la reserva asociada al pago
     * 
     * @return Reserva asociada al pago
     */
    public Reserva getReserva() {
        return reserva;
    }

    /**
     * Obtiene el total del pago
     * 
     * @return Total del pago
     */
    public double getTotal() {
        return total;
    }

    /**
     * Establece el total del pago
     * 
     * @param total Nuevo total del pago
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Obtiene la fecha en que se realizó el pago
     * 
     * @return Fecha del pago
     */
    public Date getFechaPago() {
        return fechaPago;
    }

    /**
     * Obtiene el estado actual del pago
     * 
     * @return Estado del pago
     */
    public EstadoPago getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pago
     * 
     * @param estado Nuevo estado del pago
     */
    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación en cadena del objeto Pago
     * 
     * @return Cadena con la información del pago
     */
    @Override
    public String toString() {
        return "Pago{" + "id=" + id + ", total=" + total + ", estado=" + estado + "}";
    }
}