package jurassicpark.modelo.gestion;

import java.util.Date;

/**
 * Clase que representa una factura de una reserva.
 */
public class Factura {
    private int id;
    private Reserva reserva;
    private double total;
    private Date fechaEmision;

    /**
     * Constructor de la clase Factura
     * 
     * @param id      Identificador único de la factura
     * @param reserva Reserva asociada a la factura
     * @param total   Total de la factura
     */
    public Factura(int id, Reserva reserva, double total) {
        this.id = id;
        this.reserva = reserva;
        this.total = total;
        this.fechaEmision = new Date(); // Fecha actual
    }

    /**
     * Genera un resumen de la factura
     * 
     * @return String con el resumen de la factura
     */
    public String generarResumen() {
        return "FACTURA #" + id + "\n" +
                "Fecha: " + fechaEmision + "\n" +
                "Cliente: " + reserva.getCliente().getNombreCompleto() + "\n" +
                "Reserva: #" + reserva.getId() + "\n" +
                "Entradas: " + reserva.getEntradas().size() + "\n" +
                "Total: $" + total;
    }

    // Getters
    /**
     * Obtiene el identificador de la factura
     * 
     * @return Identificador único de la factura
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la reserva asociada a la factura
     * 
     * @return Reserva asociada a la factura
     */
    public Reserva getReserva() {
        return reserva;
    }

    /**
     * Obtiene el total de la factura
     * 
     * @return Total de la factura
     */
    public double getTotal() {
        return total;
    }

    /**
     * Obtiene la fecha de emisión de la factura
     * 
     * @return Fecha de emisión de la factura
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Devuelve una representación en cadena del objeto Factura
     * 
     * @return Cadena con la información de la factura
     */
    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", reserva=" + reserva.getId() +
                ", total=" + total + ", fecha=" + fechaEmision + "}";
    }
}