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
        vista.mostrarMensaje("4. Crear visitantes");
        vista.mostrarMensaje("5. Asignar visitantes a entradas");
        vista.mostrarMensaje("6. Marcar entradas como utilizadas");
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
}