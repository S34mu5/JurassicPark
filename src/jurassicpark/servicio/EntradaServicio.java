package jurassicpark.servicio;

import jurassicpark.modelo.gestion.Entrada;
import jurassicpark.modelo.gestion.EntradaEstadisticas;
import jurassicpark.modelo.gestion.Factura;
import jurassicpark.modelo.gestion.Reserva;
import jurassicpark.modelo.gestion.Visitante;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con las entradas
 */
public class EntradaServicio {

    // Contador para generar IDs automáticamente
    private static int contadorEntradas = 5000; // Empezamos desde 5000

    /**
     * Genera entradas para una reserva
     * 
     * @param reserva        Reserva a la que pertenecen las entradas
     * @param cantidad       Cantidad de entradas a generar
     * @param precioUnitario Precio unitario de cada entrada
     * @return Lista de entradas generadas
     */
    public List<Entrada> generarEntradas(Reserva reserva, int cantidad, double precioUnitario) {
        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int entradaId = ++contadorEntradas; // Incrementamos el contador y asignamos el nuevo ID
            Entrada entrada = new Entrada(entradaId, reserva, precioUnitario);
            reserva.agregarEntrada(entrada);
            entradas.add(entrada);
        }
        return entradas;
    }

    /**
     * Genera entradas para una reserva con IDs específicos
     * Esta versión se mantiene para compatibilidad y propósitos de prueba
     * 
     * @param reserva        Reserva a la que pertenecen las entradas
     * @param cantidad       Cantidad de entradas a generar
     * @param precioUnitario Precio unitario de cada entrada
     * @param idInicial      ID inicial para las entradas
     * @return Lista de entradas generadas
     */
    public List<Entrada> generarEntradas(Reserva reserva, int cantidad, double precioUnitario, int idInicial) {
        List<Entrada> entradas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            int entradaId = idInicial + i;

            if (entradaId > contadorEntradas) {
                contadorEntradas = entradaId;
            }

            Entrada entrada = new Entrada(entradaId, reserva, precioUnitario);
            reserva.agregarEntrada(entrada);
            entradas.add(entrada);
        }
        return entradas;
    }

    /**
     * Calcula el precio total de una lista de entradas
     * 
     * @param entradas Lista de entradas
     * @return Precio total
     */
    public double calcularPrecioTotal(List<Entrada> entradas) {
        double total = 0;
        for (Entrada entrada : entradas) {
            total += entrada.getPrecio();
        }
        return total;
    }

    /**
     * Marca una entrada como utilizada
     * 
     * @param entrada Entrada a marcar
     * @return true si se pudo marcar correctamente
     */
    public boolean marcarEntradaComoUtilizada(Entrada entrada) {
        if (!entrada.isUtilizada()) {
            entrada.marcarComoUtilizada();
            return true;
        }
        return false;
    }

    /**
     * Asigna visitantes a las entradas
     * 
     * @param reserva    Reserva a la que pertenecen las entradas
     * @param entradas   Lista de entradas
     * @param visitantes Lista de visitantes
     */
    public void asignarVisitantes(Reserva reserva, List<Entrada> entradas, List<Visitante> visitantes) {
        int minSize = Math.min(entradas.size(), visitantes.size());
        for (int i = 0; i < minSize; i++) {
            entradas.get(i).asignarVisitante(visitantes.get(i));
        }
    }

    /**
     * Genera estadísticas sobre las entradas
     * 
     * @param entradas Lista de entradas
     * @param factura  Factura asociada
     * @return Objeto con las estadísticas
     */
    public EntradaEstadisticas generarEstadisticas(List<Entrada> entradas, Factura factura) {
        EntradaEstadisticas estadisticas = new EntradaEstadisticas();

        estadisticas.setTotalEntradas(entradas.size());

        int entradasUtilizadas = 0;
        int entradasAdultos = 0;
        int entradasNinos = 0;

        for (Entrada entrada : entradas) {
            if (entrada.isUtilizada()) {
                entradasUtilizadas++;
            }

            Visitante visitante = entrada.getVisitante();
            if (visitante != null) {
                if (visitante.getTipoVisitante().equals("NIÑO")) {
                    entradasNinos++;
                } else {
                    entradasAdultos++;
                }
            }
        }

        estadisticas.setEntradasUtilizadas(entradasUtilizadas);
        estadisticas.setEntradasAdultos(entradasAdultos);
        estadisticas.setEntradasNinos(entradasNinos);
        estadisticas.setTotalFacturado(factura.getTotal());

        return estadisticas;
    }
}