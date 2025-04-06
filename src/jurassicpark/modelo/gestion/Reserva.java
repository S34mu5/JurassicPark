package jurassicpark.modelo.gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa una reserva de entradas para el parque.
 */
public class Reserva {
    public enum EstadoReserva {
        PENDIENTE, CONFIRMADA, CANCELADA
    }

    private int id;
    private Cliente cliente;
    private Date fechaReserva;
    private Date fechaVisita;
    private EstadoReserva estado;
    private List<Entrada> entradas;
    private Pago pago;
    private Factura factura;

    /**
     * Constructor de la clase Reserva
     * 
     * @param id          Identificador único de la reserva
     * @param cliente     Cliente que realiza la reserva
     * @param fechaVisita Fecha programada para la visita
     */
    public Reserva(int id, Cliente cliente, Date fechaVisita) {
        this.id = id;
        this.cliente = cliente;
        this.fechaReserva = new Date(); // Fecha actual
        this.fechaVisita = fechaVisita;
        this.estado = EstadoReserva.PENDIENTE;
        this.entradas = new ArrayList<>();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public Date getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(Date fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public List<Entrada> getEntradas() {
        return entradas;
    }

    public void agregarEntrada(Entrada entrada) {
        if (!entradas.contains(entrada)) {
            entradas.add(entrada);
        }
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public String toString() {
        return "Reserva{" + "id=" + id + ", cliente=" + cliente.getNombreCompleto() +
                ", estado=" + estado + ", entradas=" + entradas.size() + "}";
    }
}