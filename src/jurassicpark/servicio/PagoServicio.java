package jurassicpark.servicio;

import jurassicpark.modelo.gestion.Pago;
import jurassicpark.modelo.gestion.Reserva;

/**
 * Servicio que gestiona las operaciones relacionadas con los pagos
 */
public class PagoServicio {

    // Contador para generar IDs automáticamente
    private static int contadorPagos = 3000; // Empezamos desde 3000

    /**
     * Crea un nuevo pago con ID generado automáticamente
     * 
     * @param reserva Reserva asociada al pago
     * @param total   Total del pago
     * @return Pago creado
     */
    public Pago crearPago(Reserva reserva, double total) {
        int id = ++contadorPagos; // Incrementamos el contador y asignamos el nuevo ID
        Pago pago = new Pago(id, reserva, total);
        reserva.setPago(pago);
        return pago;
    }

    /**
     * Crea un nuevo pago con ID específico
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     * 
     * @param id      ID del pago
     * @param reserva Reserva asociada al pago
     * @param total   Total del pago
     * @return Pago creado
     */
    public Pago crearPago(int id, Reserva reserva, double total) {
        // Actualizamos el contador si el ID proporcionado es mayor
        if (id > contadorPagos) {
            contadorPagos = id;
        }
        Pago pago = new Pago(id, reserva, total);
        reserva.setPago(pago);
        return pago;
    }

    /**
     * Confirma un pago
     * 
     * @param pago Pago a confirmar
     * @return Pago confirmado
     */
    public Pago confirmarPago(Pago pago) {
        pago.confirmarPago();
        return pago;
    }

    /**
     * Cancela un pago
     * 
     * @param pago Pago a cancelar
     * @return Pago cancelado
     */
    public Pago cancelarPago(Pago pago) {
        pago.cancelarPago();
        return pago;
    }
}