package jurassicpark.vista;

import jurassicpark.modelo.gestion.Cliente;
import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Visitante;

import java.util.List;
import java.util.Scanner;

/**
 * Clase que implementa la interfaz de usuario mediante consola
 */
public class ConsolaVista {
    private Scanner scanner;

    /**
     * Constructor de la clase ConsolaVista
     */
    public ConsolaVista() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Muestra la bienvenida con ASCII art
     */
    public void mostrarBienvenida() {

        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀  ⠀⠀⠀⠀⢀⣀⣤⣤⣴⣶⣶⣶⣶⣶⣦⣤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣶⣿⡿⠿⠛⠛⠋⠉⠉⠉⠉⠉⠙⠛⠻⠿⣿⣷⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀ ⣠⣾⣿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀     ⠀⠀⠉⠛⢿⣿⣦⣄⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⣠⣾⡿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣄⣀⠀⠀⠀⠀⠀⠀    ⠈⠻⣿⣷⡄⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⢀⣾⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣴⣾⣿⣿⣿⡿⣶⣦⡀⠀⠀⠀⠀   ⠀ ⠻⣿⣦⡀⠀⠀⠀");
        System.out.println("⠀⠀⢠⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⢤⣤⣿⣿⣿⣿⣿⣿⣿⣆⠘⣿⠟⣷⣦⣄⡀⠀⠀⠀   ⠘⣿⣷⡀⠀⠀");
        System.out.println("⠀⢠⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⢶⣤⣽⣿⣿⣿⣿⣿⣿⣿⡋⢠⣿⡧⠈⠙⣿⣿⣿⣶⣦⡄   ⠘⣿⣷⡀⠀");
        System.out.println("⠀⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⣦⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣴⣯⣩⣿⣿⣿⣿⣦   ⠘⣿⣧⠀");
        System.out.println("⢸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠉⠉⠉⠛⢻⣿⢿⡿⡿⡿⢿⡟⠀   ⢻⣿⡄");
        System.out.println("⣾⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢲⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣳⠀⠀⠀⠹⠋⠛⠽⠟⠋⠋⠀    ⠀⢸⣿⡇");
        System.out.println("⣿⣿⠀⠀⠀⠀⠀⠀⠀⢀⡀⣦⣪⣿⣿⣿⣿⣿⡿⠹⠹⣿⣿⣿⣿⣷⣓⣴⢀⡀⡀⠀⠀⠀⠀⠀     ⠀⢸⣿⡇");
        System.out.println("⣿⣿⠀⠀⠀⠀⠀⣤⣳⣶⣿⣿⣿⣿⣿⣿⡏⡿⠃⢀⡀⠈⠉⠛⠻⠿⣿⣷⣽⣟⠷⠶⡄⠀⠀⠀⠀    ⢸⣿⡇");
        System.out.println("⢸⣿⡆⣤⣄⣻⣷⣿⣿⣿⣿⣿⣿⡿⣿⡁⠇⠁⠀⢻⡞⠉⠀⠀⠀⠀⠀⠙⠻⠿⠟⠛⠀⠀⠀⠀   ⠀ ⣸⣿⠇");
        System.out.println("⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣄⣠⣠⡴⠿⣣⣿⡵⠧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⢀⣿⡿⠀");
        System.out.println("⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⣿⠿⣿⣿⣿⣿⣴⡞⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀   ⠀⣾⣿⠃⠀");
        System.out.println("⠀⠀⠹⣿⣿⣿⣿⣿⣿⢻⠿⣆⠘⠢⡘⠘⠛⠛⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀    ⢀⣼⣿⠃⠀⠀");
        System.out.println("⠀⠀⠀⠙⣿⣿⣿⡏⠙⢦⣣⠈⠖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣄⣀⢀⣦⣀⠀⢀  ⣠⣾⡿⠃⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠈⠻⣿⣧⡀⠀⠉⠣⡀⠀⠀⣠⣤⣀⣠⣤⣐⣴⣷⣅⣤⣦⣟⣿⢿⣸⣿⣿⣾⣿⣿⠟⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠉⠻⣿⣷⣄⣀⣴⣦⣤⣽⣟⣙⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀");
        System.out.println("⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠻⠿⠿⣿⣿⣿⣿⣿⣿⣿⠿⠿⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀");

        System.out.println("*************************************************");
        System.out.println("*   SISTEMA DE GESTIÓN DE JURASSIC PARK         *");
        System.out.println("*************************************************");
        System.out.println("\nBienvenido al sistema de gestión de Jurassic Park.");
        System.out.println("Este sistema le permite gestionar reservas, entradas, pagos y visitantes.\n");
    }

    /**
     * Muestra un mensaje en la consola
     * 
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Muestra un mensaje de error en la consola
     * 
     * @param mensaje Mensaje de error a mostrar
     */
    public void mostrarError(String mensaje) {
        System.out.println("\nERROR: " + mensaje);
    }

    /**
     * Lee una entrada del usuario
     * 
     * @param prompt Mensaje de solicitud
     * @return Entrada del usuario
     */
    public String leerEntrada(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Muestra la información de un cliente
     * 
     * @param cliente Cliente a mostrar
     */
    public void mostrarCliente(Cliente cliente) {
        System.out.println("\nINFORMACIÓN DEL CLIENTE");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Apellido: " + cliente.getApellido());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("Teléfono: " + cliente.getTelefono());
    }

    /**
     * Muestra la información de una reserva
     * 
     * @param reserva Reserva a mostrar
     */
    public void mostrarReserva(Reserva reserva) {
        System.out.println("\nINFORMACIÓN DE LA RESERVA");
        System.out.println("ID: " + reserva.getId());
        System.out.println("Cliente: " + reserva.getCliente().getNombreCompleto());
        System.out.println("Fecha de reserva: " + reserva.getFechaReserva());
        System.out.println("Fecha de visita: " + reserva.getFechaVisita());
        System.out.println("Estado: " + reserva.getEstado());
        System.out.println("Entradas: " + reserva.getEntradas().size());
    }

    /**
     * Muestra la información de un listado de entradas
     * 
     * @param entradas Lista de entradas a mostrar
     */
    public void mostrarEntradas(List<Entrada> entradas) {
        System.out.println("\nLISTA DE ENTRADAS");
        System.out.println("Total: " + entradas.size());

        for (Entrada entrada : entradas) {
            String visitanteInfo = (entrada.tieneVisitanteAsignado())
                    ? entrada.getVisitante().getNombreCompleto()
                    : "Sin asignar";

            System.out.println("\nID: " + entrada.getId());
            System.out.println("Precio: $" + entrada.getPrecio());
            System.out.println("Visitante: " + visitanteInfo);
            System.out.println("Utilizada: " + (entrada.isUtilizada() ? "Sí" : "No"));
        }
    }
}
