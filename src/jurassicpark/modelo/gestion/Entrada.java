package jurassicpark.modelo.gestion;

import java.util.Date;

/**
 * Clase que representa una entrada para el parque.
 */
public class Entrada {
    private int id;
    private Reserva reserva;
    private Visitante visitante;
    private double precio;
    private Date fechaEmision;
    private boolean utilizada;

    /**
     * Constructor de la clase Entrada
     * 
     * @param id      Identificador único de la entrada
     * @param reserva Reserva a la que pertenece la entrada
     * @param precio  Precio de la entrada
     */
    public Entrada(int id, Reserva reserva, double precio) {
        this.id = id;
        this.reserva = reserva;
        this.precio = precio;
        this.fechaEmision = new Date(); // Fecha actual
        this.utilizada = false;
        this.visitante = null; // Inicialmente sin visitante asignado
    }

    /**
     * Asigna un visitante a la entrada
     * 
     * @param visitante Visitante a asignar
     */
    public void asignarVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    /**
     * Marca la entrada como utilizada
     */
    public void marcarComoUtilizada() {
        this.utilizada = true;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public boolean tieneVisitanteAsignado() {
        return visitante != null;
    }

    @Override
    public String toString() {
        String visitanteInfo = (visitante != null) ? visitante.getNombreCompleto() : "Sin asignar";
        return "Entrada{" + "id=" + id + ", visitante=" + visitanteInfo +
                ", precio=" + precio + ", utilizada=" + utilizada + "}";
    }
}