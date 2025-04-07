package jurassicpark.servicio;

import jurassicpark.modelo.gestion.Factura;
import jurassicpark.modelo.gestion.Reserva;

/**
 * Servicio que gestiona las operaciones relacionadas con las facturas
 */
public class FacturaServicio {

    // Contador para generar IDs automáticamente
    private static int contadorFacturas = 4000; // Empezamos desde 4000

    /**
     * Genera una nueva factura con ID generado automáticamente
     * 
     * @param reserva Reserva asociada a la factura
     * @param total   Total de la factura
     * @return Factura generada
     */
    public Factura generarFactura(Reserva reserva, double total) {
        int id = ++contadorFacturas; // Incrementamos el contador y asignamos el nuevo ID
        Factura factura = new Factura(id, reserva, total);
        reserva.setFactura(factura);
        return factura;
    }

    /**
     * Genera una nueva factura con ID específico
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     * 
     * @param id      ID de la factura
     * @param reserva Reserva asociada a la factura
     * @param total   Total de la factura
     * @return Factura generada
     */
    public Factura generarFactura(int id, Reserva reserva, double total) {
        if (id > contadorFacturas) {
            contadorFacturas = id;
        }
        Factura factura = new Factura(id, reserva, total);
        reserva.setFactura(factura);
        return factura;
    }

    /**
     * Genera un resumen de factura
     * 
     * @param factura Factura de la que se quiere obtener el resumen
     * @return Resumen de la factura
     */
    public String generarResumenFactura(Factura factura) {
        return factura.generarResumen();
    }
}