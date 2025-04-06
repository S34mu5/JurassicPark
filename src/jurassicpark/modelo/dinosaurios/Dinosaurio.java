package jurassicpark.modelo.dinosaurios;

/**
 * Clase abstracta que representa un dinosaurio en el parque.
 * Sirve como base para todos los tipos específicos de dinosaurios.
 */
public abstract class Dinosaurio {
    private int id;
    private String nombre;
    private String especie;
    private double altura; // en metros
    private double peso; // en kilogramos
    private String dieta; // "CARNÍVORO", "HERBÍVORO", "OMNÍVORO"
    private int peligrosidad; // escala del 1 al 10
    private boolean enExhibicion;

    /**
     * Constructor de la clase Dinosaurio
     * 
     * @param id           Identificador único del dinosaurio
     * @param nombre       Nombre del dinosaurio
     * @param especie      Especie del dinosaurio
     * @param altura       Altura en metros
     * @param peso         Peso en kilogramos
     * @param dieta        Tipo de alimentación
     * @param peligrosidad Nivel de peligrosidad (1-10)
     */
    public Dinosaurio(int id, String nombre, String especie, double altura, double peso, String dieta,
            int peligrosidad) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.altura = altura;
        this.peso = peso;
        this.dieta = dieta;
        this.peligrosidad = peligrosidad;
        this.enExhibicion = true; // Por defecto está en exhibición
    }

    /**
     * Método abstracto que debe implementarse en las clases hijas
     * para definir el sonido característico de cada especie
     * 
     * @return Descripción del sonido emitido
     */
    public abstract String emitirSonido();

    /**
     * Método abstracto que debe implementarse en las clases hijas
     * para definir el comportamiento de alimentación específico
     * 
     * @return Descripción del comportamiento alimenticio
     */
    public abstract String alimentarse();

    /**
     * Método abstracto que debe implementarse en las clases hijas
     * para definir las medidas de seguridad necesarias para la especie
     * 
     * @return Listado de medidas de seguridad recomendadas
     */
    public abstract String medidasDeSeguridad();

    /**
     * Método concreto que está implementado para todos los dinosaurios
     * 
     * @return Información general del dinosaurio
     */
    public String obtenerInformacion() {
        return "Dinosaurio #" + id + ": " + nombre + " (" + especie + ")\n" +
                "Características físicas: " + altura + "m, " + peso + "kg\n" +
                "Dieta: " + dieta + "\n" +
                "Nivel de peligrosidad: " + peligrosidad + "/10\n" +
                "Estado: " + (enExhibicion ? "En exhibición" : "No disponible");
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDieta() {
        return dieta;
    }

    public int getPeligrosidad() {
        return peligrosidad;
    }

    public void setPeligrosidad(int peligrosidad) {
        if (peligrosidad >= 1 && peligrosidad <= 10) {
            this.peligrosidad = peligrosidad;
        }
    }

    public boolean isEnExhibicion() {
        return enExhibicion;
    }

    public void setEnExhibicion(boolean enExhibicion) {
        this.enExhibicion = enExhibicion;
    }

    @Override
    public String toString() {
        return especie + " #" + id + " - " + nombre;
    }
}