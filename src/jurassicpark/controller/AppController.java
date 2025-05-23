package jurassicpark.controller;

import jurassicpark.modelo.gestion.*;
import jurassicpark.servicio.*;
import jurassicpark.vista.ConsolaVista;
import jurassicpark.util.MenuManager;
import jurassicpark.util.InputValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


/**
 * Controlador principal de la aplicación Gestiona la interacción entre los
 * servicios y la vista
 */
public class AppController {

    // Servicios
    private final ClienteServicio clienteServicio;
    private final ReservaServicio reservaServicio;
    private final EntradaServicio entradaServicio;
    private final VisitanteServicio visitanteServicio;

    // Vista
    private final ConsolaVista vista;

    // Gestor de menús
    private final MenuManager menuManager;

    // Estado de la aplicación
    private Cliente clienteActual;
    private Reserva reservaActual;
    private List<Entrada> entradasActuales;
    private List<Visitante> visitantesActuales;

    // Scanner para entrada de datos
    private final Scanner scanner;

    /**
     * Constructor de la clase AppController
     */
    public AppController() {
        this.clienteServicio = new ClienteServicio();
        this.reservaServicio = new ReservaServicio();
        this.entradaServicio = new EntradaServicio();
        this.visitanteServicio = new VisitanteServicio();

        this.vista = new ConsolaVista();
        this.menuManager = new MenuManager(vista);

        this.entradasActuales = new ArrayList<>();
        this.visitantesActuales = new ArrayList<>();

        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia la aplicación
     */
    public void iniciar() {
        mostrarBienvenida();
        ejecutarMenuPrincipal();
    }

    /**
     * Muestra el mensaje de bienvenida con ASCII art
     */
    private void mostrarBienvenida() {
        vista.mostrarBienvenida();
    }

    /**
     * Ejecuta el bucle principal del menú
     */
    private void ejecutarMenuPrincipal() {
        int opcion;
        do {
            menuManager.mostrarMenuPrincipal();
            opcion = InputValidator.validarEnteroPositivo(scanner.nextLine(), -1);

            switch (opcion) {
                case 1 ->
                    this.crearCliente();
                case 2 ->
                    this.crearReserva();
                case 3 ->
                    this.generarEntradas();
                case 4 ->
                    this.crearVisitantes();
                case 5 ->
                    this.asignarVisitantesAEntradas();
                case 6 ->
                    this.marcarEntradasComoUtilizadas();
                case 0 ->
                    this.vista.mostrarMensaje("¡Gracias por usar el sistema!");
                default ->
                    this.vista.mostrarError("Opción no válida");
            }
        } while (opcion != 0);
    }

    // Implementación de las opciones del menú
    /**
     * Opción 1: Crear un nuevo cliente
     */
    private void crearCliente() {
        menuManager.mostrarSubmenu("CREAR CLIENTE");

        vista.mostrarMensaje("Nombre: ");
        String nombre = scanner.nextLine();

        vista.mostrarMensaje("Apellido: ");
        String apellido = scanner.nextLine();

        vista.mostrarMensaje("Email: ");
        String email = scanner.nextLine();

        vista.mostrarMensaje("Teléfono: ");
        String telefono = scanner.nextLine();

        clienteActual = clienteServicio.crearCliente(nombre, apellido, email, telefono);
        vista.mostrarMensaje("Cliente creado: " + clienteActual.getNombreCompleto());
    }

    /**
     * Opción 2: Crear una nueva reserva
     */
    private void crearReserva() {
        menuManager.mostrarSubmenu("CREAR RESERVA");

        // Mostrar lista de clientes disponibles
        List<Cliente> clientes = clienteServicio.obtenerTodosLosClientes();
        if (clientes.isEmpty()) {
            vista.mostrarError("No hay clientes registrados. Debe crear uno primero.");
            return;
        }

        vista.mostrarMensaje("\nClientes disponibles:");
        for (Cliente cliente : clientes) {
            vista.mostrarMensaje(cliente.getId() + ". " + cliente.getNombreCompleto());
        }

        // Seleccionar cliente
        vista.mostrarMensaje("\nSeleccione el ID del cliente: ");
        String idStr = scanner.nextLine();
        if (!InputValidator.esNumero(idStr)) {
            vista.mostrarError("ID inválido. Debe ser un número.");
            return;
        }

        int idCliente = Integer.parseInt(idStr);
        Cliente clienteSeleccionado = clienteServicio.buscarClientePorId(idCliente);
        if (clienteSeleccionado == null) {// Es decir, dado que buscarClientePorId() retorna un Cliente, si no encuentra
            // devolverá null según se ha definido el método.
            vista.mostrarError("Cliente no encontrado.");
            return;
        }

        // Pedir fecha de visita
        vista.mostrarMensaje("\nIngrese la fecha de visita (formato DD/MM/AAAA):");
        String fechaStr = scanner.nextLine();

        if (!InputValidator.esFechaValida(fechaStr)) {
            vista.mostrarError("Fecha de visita inválida. Operación cancelada.");
            return;
        }

        Date fechaVisita = InputValidator.convertirAFecha(fechaStr);
        reservaActual = reservaServicio.crearReserva(clienteSeleccionado, fechaVisita);
        clienteActual = clienteSeleccionado;
        vista.mostrarMensaje("Reserva creada: #" + reservaActual.getId()
                + " para cliente: " + clienteSeleccionado.getNombreCompleto()
                + " en fecha: " + fechaVisita);
    }

    /**
     * Opción 3: Generar entradas para la reserva actual
     */
    private void generarEntradas() {
        menuManager.mostrarSubmenu("GENERAR ENTRADAS");

        // Mostrar lista de reservas disponibles
        List<Reserva> reservas = reservaServicio.obtenerTodasLasReservas();
        if (reservas.isEmpty()) {
            vista.mostrarError("No hay reservas registradas. Debe crear una primero.");
            return;
        }

        vista.mostrarMensaje("\nReservas disponibles:");
        for (Reserva reserva : reservas) {
            vista.mostrarMensaje(reserva.getId() + ". Cliente: " + reserva.getCliente().getNombreCompleto()
                    + ", Fecha: " + reserva.getFechaVisita());
        }

        // Seleccionar reserva
        vista.mostrarMensaje("\nSeleccione el ID de la reserva: ");
        String idStr = scanner.nextLine();
        if (!InputValidator.esNumero(idStr)) {
            vista.mostrarError("ID inválido. Debe ser un número.");
            return;
        }

        int idReserva = Integer.parseInt(idStr);
        reservaActual = reservaServicio.buscarReservaPorId(idReserva);
        if (reservaActual == null) {
            vista.mostrarError("Reserva no encontrada.");
            return;
        }

        // Pedir cantidad de entradas
        vista.mostrarMensaje("\nCantidad de entradas: ");
        int cantidad = InputValidator.validarEnteroPositivo(scanner.nextLine(), 0);
        if (cantidad <= 0) {
            vista.mostrarError("La cantidad debe ser mayor a 0.");
            return;
        }

        // Pedir precio unitario
        vista.mostrarMensaje("Precio unitario ($): ");
        double precio = InputValidator.validarEnteroPositivo(scanner.nextLine(), 0);
        if (precio <= 0) {
            vista.mostrarError("El precio debe ser mayor a 0.");
            return;
        }

        entradasActuales = entradaServicio.generarEntradas(reservaActual, cantidad, precio);

        // Guardar la reserva con las entradas en la base de datos
        try {
            reservaServicio.actualizarReserva(reservaActual);
            vista.mostrarMensaje("Se generaron " + cantidad + " entradas para la reserva #"
                    + reservaActual.getId() + " con precio unitario $" + precio);
        } catch (Exception e) {
            vista.mostrarError("Error al guardar las entradas: " + e.getMessage());
        }
    }

    /**
     * Opción 4: Crear visitantes
     */
    private void crearVisitantes() {
        menuManager.mostrarSubmenu("CREAR VISITANTES");

        // Obtener todas las reservas
        List<Reserva> todasLasReservas = reservaServicio.obtenerTodasLasReservas();

        List<Reserva> reservasDisponibles = new ArrayList<>();

        // Filtrar reservas donde NINGUNA entrada tiene visitante asignado
        for (Reserva reserva : todasLasReservas) {
            if (!reserva.getEntradas().isEmpty()) {
                boolean todasEntradasSinVisitantes = true;
                for (Entrada entrada : reserva.getEntradas()) {
                    if (entrada.tieneVisitanteAsignado()) {
                        todasEntradasSinVisitantes = false;
                        break;
                    }
                }
                if (todasEntradasSinVisitantes) {
                    reservasDisponibles.add(reserva);
                }
            }
        }

        if (reservasDisponibles.isEmpty()) {
            vista.mostrarError("No hay reservas con entradas disponibles para asignar visitantes.");
            return;
        }

        // Mostrar reservas disponibles
        vista.mostrarMensaje("\nReservas con entradas sin visitantes:");
        for (Reserva reserva : reservasDisponibles) {
            vista.mostrarMensaje(reserva.getId() + ". Cliente: " + reserva.getCliente().getNombreCompleto()
                    + ", Entradas: " + reserva.getEntradas().size());
        }

        // Seleccionar reserva
        vista.mostrarMensaje("\nSeleccione el ID de la reserva: ");
        String idStr = scanner.nextLine();
        if (!InputValidator.esNumero(idStr)) {
            vista.mostrarError("ID inválido. Debe ser un número.");
            return;
        }
        int idReserva = Integer.parseInt(idStr);
        Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);

        if (reservaSeleccionada == null || reservaSeleccionada.getEntradas().isEmpty()) {
            vista.mostrarError("Reserva no válida o sin entradas.");
            return;
        }

        // Verificamos que la reserva seleccionada está en la lista de reservas
        // disponibles
        boolean reservaEsValida = false;
        for (Reserva r : reservasDisponibles) {
            if (r.getId() == reservaSeleccionada.getId()) {
                reservaEsValida = true;
                break;
            }
        }

        if (!reservaEsValida) {
            vista.mostrarError("La reserva seleccionada no está disponible para asignar visitantes.");
            return;
        }

        // Inicializar lista de visitantes y obtener la cantidad de entradas
        List<Visitante> visitantes = new ArrayList<>();
        int cantidadEntradas = reservaSeleccionada.getEntradas().size();

        vista.mostrarMensaje("\nDebe crear " + cantidadEntradas + " visitantes para esta reserva.");

        // Obtener la lista de clientes disponibles una sola vez
        List<Cliente> clientes = clienteServicio.obtenerTodosLosClientes();

        // Crear cada visitante uno por uno
        for (int i = 0; i < cantidadEntradas; i++) {
            vista.mostrarMensaje("\n--- Visitante " + (i + 1) + " de " + cantidadEntradas + " ---");

            // Preguntar si desea reutilizar datos de cliente para este visitante
            vista.mostrarMensaje("¿Desea reutilizar los datos de un cliente para este visitante? (S/N): ");
            String respuesta = scanner.nextLine().toUpperCase();
            boolean reutilizarDatos = respuesta.equals("S");

            if (reutilizarDatos) {
                // Mostrar lista de clientes disponibles
                vista.mostrarMensaje("\nClientes disponibles:");
                for (Cliente cliente : clientes) {
                    vista.mostrarMensaje(cliente.getId() + ". " + cliente.getNombreCompleto());
                }

                // Seleccionar cliente
                vista.mostrarMensaje("\nSeleccione el ID del cliente a reutilizar: ");
                idStr = scanner.nextLine();
                if (!InputValidator.esNumero(idStr)) {
                    vista.mostrarError("ID inválido. Debe ser un número.");
                    return;
                }
                int idCliente = Integer.parseInt(idStr);
                Cliente clienteSeleccionado = clienteServicio.buscarClientePorId(idCliente);

                if (clienteSeleccionado == null) {
                    vista.mostrarError("Cliente no encontrado.");
                    return;
                }

                // Preguntar edad para este visitante
                vista.mostrarMensaje("Edad del visitante " + clienteSeleccionado.getNombreCompleto() + ": ");
                String edadStr = scanner.nextLine();
                if (!InputValidator.esNumero(edadStr)) {
                    vista.mostrarError("Edad inválida. Debe ser un número.");
                    return;
                }
                int edad = Integer.parseInt(edadStr);
                if (edad <= 0 || edad > 120) {
                    vista.mostrarError("Edad fuera de rango válido (1-120).");
                    return;
                }

                // Crear visitante con los datos del cliente
                Visitante visitante = visitanteServicio.crearVisitante(
                        clienteSeleccionado.getNombre(),
                        clienteSeleccionado.getApellido(),
                        edad);
                visitantes.add(visitante);
                vista.mostrarMensaje(
                        "Visitante " + (i + 1) + " creado: " + visitante.getNombreCompleto() + " (" + edad + " años)");

            } else {
                // Crear visitante manualmente
                vista.mostrarMensaje("Nombre: ");
                String nombre = scanner.nextLine();
                vista.mostrarMensaje("Apellido: ");
                String apellido = scanner.nextLine();
                vista.mostrarMensaje("Edad: ");
                String edadStr = scanner.nextLine();

                if (!InputValidator.esNumero(edadStr)) {
                    vista.mostrarError("Edad inválida. Debe ser un número.");
                    return;
                }
                int edad = Integer.parseInt(edadStr);
                if (edad <= 0 || edad > 120) {
                    vista.mostrarError("Edad fuera de rango válido (1-120).");
                    return;
                }

                Visitante visitante = visitanteServicio.crearVisitante(nombre, apellido, edad);
                visitantes.add(visitante);
                vista.mostrarMensaje(
                        "Visitante " + (i + 1) + " creado: " + visitante.getNombreCompleto() + " (" + edad + " años)");
            }
        }

        // Asignar visitantes a las entradas
        entradaServicio.asignarVisitantes(reservaSeleccionada,
                reservaSeleccionada.getEntradas(),
                visitantes);

        // Actualizar la reserva en la base de datos
        try {
            reservaServicio.actualizarReserva(reservaSeleccionada);
            vista.mostrarMensaje("\nSe han creado y asignado " + visitantes.size() + " visitantes a la reserva #"
                    + reservaSeleccionada.getId());
        } catch (Exception e) {
            vista.mostrarError("Error al guardar la asignación de visitantes: " + e.getMessage());
        }

        // Guardar los visitantes creados
        visitantesActuales = visitantes;
        vista.mostrarMensaje("Los visitantes han sido guardados para posibles asignaciones futuras.");
    }

    /**
     * Opción 5: Asignar visitantes
     */
    private void asignarVisitantesAEntradas() {
        menuManager.mostrarSubmenu("ASIGNAR VISITANTES A ENTRADAS");

        // Verificar que haya visitantes disponibles
        if (visitantesActuales == null || visitantesActuales.isEmpty()) {
            vista.mostrarError("No hay visitantes disponibles. Debe crear visitantes primero.");
            return;
        }

        // Mostrar lista de reservas con entradas sin visitantes asignados
        List<Reserva> reservas = reservaServicio.obtenerTodasLasReservas();
        List<Reserva> reservasConEntradasSinVisitantes = new ArrayList<>();

        // Filtrar reservas donde NINGUNA entrada tiene visitante asignado
        for (Reserva reserva : reservas) {
            if (!reserva.getEntradas().isEmpty()) {
                boolean todasEntradasSinVisitantes = true;
                for (Entrada entrada : reserva.getEntradas()) {
                    if (entrada.tieneVisitanteAsignado()) {
                        todasEntradasSinVisitantes = false;
                        break;
                    }
                }
                if (todasEntradasSinVisitantes) {
                    reservasConEntradasSinVisitantes.add(reserva);
                }
            }
        }

        if (reservasConEntradasSinVisitantes.isEmpty()) {
            vista.mostrarError("No hay reservas con entradas disponibles para asignar visitantes.");
            return;
        }

        // Mostrar las reservas disponibles
        vista.mostrarMensaje("\nReservas con entradas sin visitantes asignados:");
        for (Reserva reserva : reservasConEntradasSinVisitantes) {
            vista.mostrarMensaje(reserva.getId() + ". Cliente: " + reserva.getCliente().getNombreCompleto()
                    + ", Entradas: " + reserva.getEntradas().size());
        }

        // Seleccionar reserva
        vista.mostrarMensaje("\nSeleccione el ID de la reserva: ");
        String idStr = scanner.nextLine();
        if (!InputValidator.esNumero(idStr)) {
            vista.mostrarError("ID inválido. Debe ser un número.");
            return;
        }

        int idReserva = Integer.parseInt(idStr);
        Reserva reservaSeleccionada = reservaServicio.buscarReservaPorId(idReserva);
        if (reservaSeleccionada == null) {
            vista.mostrarError("Reserva no encontrada.");
            return;
        }

        // Verificamos que la reserva seleccionada está en la lista de reservas
        // disponibles
        boolean reservaEsValida = false;
        for (Reserva r : reservasConEntradasSinVisitantes) {
            if (r.getId() == reservaSeleccionada.getId()) {
                reservaEsValida = true;
                break;
            }
        }

        if (!reservaEsValida) {
            vista.mostrarError("La reserva seleccionada no está disponible para asignar visitantes.");
            return;
        }

        // Obtener las entradas sin visitantes asignados
        List<Entrada> entradasSinVisitantes = new ArrayList<>();
        for (Entrada entrada : reservaSeleccionada.getEntradas()) {
            if (!entrada.tieneVisitanteAsignado()) {
                entradasSinVisitantes.add(entrada);
            }
        }

        if (entradasSinVisitantes.isEmpty()) {
            vista.mostrarError("Esta reserva no tiene entradas sin visitantes asignados.");
            return;
        }

        // Mostrar los visitantes disponibles
        vista.mostrarMensaje("\nVisitantes disponibles:");
        for (int i = 0; i < visitantesActuales.size(); i++) {
            Visitante visitante = visitantesActuales.get(i);
            vista.mostrarMensaje((i + 1) + ". " + visitante.getNombreCompleto()
                    + " (" + visitante.getEdad() + " años, " + visitante.getTipoVisitante() + ")");
        }

        // Asignar visitantes a entradas individualmente
        List<Visitante> visitantesAsignados = new ArrayList<>();

        for (int i = 0; i < entradasSinVisitantes.size(); i++) {
            Entrada entrada = entradasSinVisitantes.get(i);

            vista.mostrarMensaje("\n--- Asignación para Entrada #" + entrada.getId() + " ---");

            // Mostrar solo los visitantes no asignados aún
            List<Visitante> visitantesDisponibles = new ArrayList<>();
            for (Visitante visitante : visitantesActuales) {
                if (!visitantesAsignados.contains(visitante)) {
                    visitantesDisponibles.add(visitante);
                }
            }

            if (visitantesDisponibles.isEmpty()) {
                vista.mostrarError("No hay más visitantes disponibles para asignar.");
                break;
            }

            vista.mostrarMensaje("Visitantes disponibles para esta entrada:");
            for (int j = 0; j < visitantesDisponibles.size(); j++) {
                Visitante visitante = visitantesDisponibles.get(j);
                vista.mostrarMensaje((j + 1) + ". " + visitante.getNombreCompleto()
                        + " (" + visitante.getEdad() + " años, " + visitante.getTipoVisitante() + ")");
            }

            vista.mostrarMensaje("\nSeleccione el número del visitante para esta entrada: ");
            String seleccionStr = scanner.nextLine();

            if (!InputValidator.esNumero(seleccionStr)) {
                vista.mostrarError("Número inválido. Se omitirá esta entrada.");
                continue;
            }

            int seleccion = Integer.parseInt(seleccionStr);
            if (seleccion < 1 || seleccion > visitantesDisponibles.size()) {
                vista.mostrarError("Selección fuera de rango. Se omitirá esta entrada.");
                continue;
            }

            Visitante visitanteSeleccionado = visitantesDisponibles.get(seleccion - 1);

            // Asignar el visitante a esta entrada
            entrada.asignarVisitante(visitanteSeleccionado);
            visitantesAsignados.add(visitanteSeleccionado);

            vista.mostrarMensaje("Visitante " + visitanteSeleccionado.getNombreCompleto()
                    + " asignado a la entrada #" + entrada.getId());
        }

        vista.mostrarMensaje("\nSe han asignado " + visitantesAsignados.size()
                + " visitantes a las entradas de la reserva #" + reservaSeleccionada.getId());
    }

    /**
     * Opción 6: Marcar entradas utilizadas
     */
    private void marcarEntradasComoUtilizadas() {
        menuManager.mostrarSubmenu("MARCAR ENTRADAS COMO UTILIZADAS");

        // Mostrar lista de reservas con entradas
        List<Reserva> reservas = reservaServicio.obtenerTodasLasReservas();
        List<Reserva> reservasConEntradas = new ArrayList<>();

        // Filtrar reservas con entradas
        for (Reserva reserva : reservas) {
            if (!reserva.getEntradas().isEmpty()) {
                reservasConEntradas.add(reserva);
            }
        }

        if (reservasConEntradas.isEmpty()) {
            vista.mostrarError("No hay reservas con entradas para marcar como utilizadas.");
            return;
        }

        vista.mostrarMensaje("\nReservas con entradas:");
        for (Reserva reserva : reservasConEntradas) {
            vista.mostrarMensaje(reserva.getId() + ". Cliente: " + reserva.getCliente().getNombreCompleto()
                    + ", Entradas: " + reserva.getEntradas().size());
        }

        // Seleccionar reserva
        vista.mostrarMensaje("\nSeleccione el ID de la reserva: ");
        String idStr = scanner.nextLine();
        if (!InputValidator.esNumero(idStr)) {
            vista.mostrarError("ID inválido. Debe ser un número.");
            return;
        }

        int idReserva = Integer.parseInt(idStr);
        reservaActual = reservaServicio.buscarReservaPorId(idReserva);
        if (reservaActual == null || reservaActual.getEntradas().isEmpty()) {
            vista.mostrarError("Reserva no encontrada o sin entradas.");
            return;
        }

        // Marcar entradas como utilizadas
        for (Entrada entrada : reservaActual.getEntradas()) {
            entradaServicio.marcarEntradaComoUtilizada(entrada);
        }
        vista.mostrarMensaje("Entradas marcadas como utilizadas");
    }
}
