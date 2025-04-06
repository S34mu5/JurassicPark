package jurassicpark.modelo.gestion;

/**
 * Clase que encapsula las estadísticas de las entradas
 */
public class EntradaEstadisticas {
    private int totalEntradas;
    private int entradasUtilizadas;
    private int entradasAdultos;
    private int entradasNinos;
    private double totalFacturado;

    public EntradaEstadisticas() {
        this.totalEntradas = 0;
        this.entradasUtilizadas = 0;
        this.entradasAdultos = 0;
        this.entradasNinos = 0;
        this.totalFacturado = 0.0;
    }

    // Getters y setters
    public int getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(int totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public int getEntradasUtilizadas() {
        return entradasUtilizadas;
    }

    public void setEntradasUtilizadas(int entradasUtilizadas) {
        this.entradasUtilizadas = entradasUtilizadas;
    }

    public int getEntradasAdultos() {
        return entradasAdultos;
    }

    public void setEntradasAdultos(int entradasAdultos) {
        this.entradasAdultos = entradasAdultos;
    }

    public int getEntradasNinos() {
        return entradasNinos;
    }

    public void setEntradasNinos(int entradasNinos) {
        this.entradasNinos = entradasNinos;
    }

    public double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }
}