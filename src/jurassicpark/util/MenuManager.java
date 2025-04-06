package jurassicpark.util;

import jurassicpark.vista.ConsolaVista;

/**
 * Clase que gestiona la visualización de menús en la aplicación
 */
public class MenuManager {
    private final ConsolaVista vista;

    /**
     * Constructor de la clase MenuManager
     * 
     * @param vista Vista para mostrar los menús
     */
    public MenuManager(ConsolaVista vista) {
        this.vista = vista;
    }

    /**
     * Muestra el menú principal de la aplicación
     */
    public void mostrarMenuPrincipal() {
        vista.mostrarMensaje("\n----- MENÚ PRINCIPAL -----");
        vista.mostrarMensaje("1. Crear cliente");
        vista.mostrarMensaje("2. Crear reserva");
        vista.mostrarMensaje("3. Generar entradas");
        vista.mostrarMensaje("4. Procesar pago");
        vista.mostrarMensaje("5. Confirmar pago");
        vista.mostrarMensaje("6. Generar factura");
        vista.mostrarMensaje("7. Crear visitantes");
        vista.mostrarMensaje("8. Asignar visitantes a entradas");
        vista.mostrarMensaje("9. Marcar entradas como utilizadas");
        vista.mostrarMensaje("10. Mostrar estadísticas");
        vista.mostrarMensaje("0. Salir");
        vista.mostrarMensaje("\nSeleccione una opción: ");
    }

    /**
     * Muestra un submenú con el título especificado
     * 
     * @param titulo Título del submenú
     */
    public void mostrarSubmenu(String titulo) {
        vista.mostrarMensaje("\n----- MENÚ > " + titulo + " -----");
    }

    /**
     * Muestra el menú de estadísticas y devuelve la opción seleccionada
     * 
     * @return Opción seleccionada
     */
    public int mostrarMenuEstadisticas() {
        System.out.println("\n----- MENÚ ESTADÍSTICAS -----");
        System.out.println("1. Total de entradas vendidas");
        System.out.println("2. Total de entradas utilizadas");
        System.out.println("3. Total facturado");
        System.out.println("4. Distribución por tipos de visitante");
        System.out.println("0. Volver al menú principal");

        String opcion = vista.leerEntrada("\nSeleccione una opción: ");
        return InputValidator.validarEnteroPositivo(opcion, -1);
    }
}