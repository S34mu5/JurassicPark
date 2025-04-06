package jurassicpark;


import jurassicpark.controller.AppController;


/**
 * Clase principal que inicia la aplicación
 */
public class Main {

    /**
     * Método principal que inicia la aplicación
     *
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        AppController controlador = new AppController();
        controlador.iniciar();

//         1. Creación de objetos básicos
//        Cliente c = new Cliente(0, "s", "s", "s", "s");
//        Date fecha = new Date();
//        Reserva r = new Reserva(12, c, fecha);
//        Entrada e = new Entrada(12, r, 200);

//         2. Añadir la entrada a la reserva
//        r.agregarEntrada(e);
//
//         3. Crear un visitante para asignar a la entrada
//        Visitante v = new Visitante(33, "Nombre", "Apellido", 25);
//
//         4. Asignar el visitante a la entrada
//        e.asignarVisitante(v);
//
//         5. Crear un pago para la reserva
//        Pago p = new Pago(44, r, 200);
//        r.setPago(p);
//
//         6. Confirmar el pago
//        p.confirmarPago();
//
//         7. Actualizar el estado de la reserva basado en el pago
//        r.setEstado(Reserva.EstadoReserva.CONFIRMADA);
//
//         8. Generar una factura
//        Factura f = new Factura(55, r, 200);
//        r.setFactura(f);
//
//         9. Marcar la entrada como utilizada
//        e.marcarComoUtilizada();
//
//         10. Generar un resumen de la factura
//        String resumenFactura = f.generarResumen();
//        System.out.println(resumenFactura);

    }
}
