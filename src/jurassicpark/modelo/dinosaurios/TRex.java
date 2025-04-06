package jurassicpark.modelo.dinosaurios;

import jurassicpark.modelo.dinosaurios.Dinosaurio;

/**
 * Clase que representa un Tyrannosaurus Rex en el parque.
 * Hereda de la clase abstracta Dinosaurio.
 */
public class TRex extends Dinosaurio {

    private int fuerzaMordida; // en kilonewtons
    private boolean alfaDeLaManada;

    /**
     * Constructor de la clase TRex
     * 
     * @param id             Identificador único del dinosaurio
     * @param nombre         Nombre del T-Rex
     * @param altura         Altura en metros
     * @param peso           Peso en kilogramos
     * @param peligrosidad   Nivel de peligrosidad (1-10)
     * @param fuerzaMordida  Fuerza de mordida en kilonewtons
     * @param alfaDeLaManada Indica si es el alfa de la manada
     */
    public TRex(int id, String nombre, double altura, double peso, int peligrosidad,
            int fuerzaMordida, boolean alfaDeLaManada) {
        super(id, nombre, "Tyrannosaurus Rex", altura, peso, "CARNÍVORO", peligrosidad);
        this.fuerzaMordida = fuerzaMordida;
        this.alfaDeLaManada = alfaDeLaManada;
    }

    /**
     * Implementación del método abstracto para el sonido característico
     */
    @Override
    public String emitirSonido() {
        return "¡GROOOOAAARRRR! Un rugido ensordecedor que puede oírse a kilómetros de distancia.";
    }

    /**
     * Implementación del método abstracto para el comportamiento alimenticio
     */
    @Override
    public String alimentarse() {
        return "Caza grandes presas como Triceratops o Edmontosaurus. " +
                "Su poderosa mandíbula puede triturar huesos con facilidad. " +
                "Se alimenta de hasta 230 kg de carne en una sola comida.";
    }

    /**
     * Implementación del método abstracto para las medidas de seguridad
     */
    @Override
    public String medidasDeSeguridad() {
        return "PELIGRO EXTREMO. Medidas de seguridad requeridas:\n" +
                "- Vallas electrificadas de 8 metros de altura mínima\n" +
                "- Doble barrera de contención\n" +
                "- Puertas de seguridad reforzadas con titanio\n" +
                "- Sistema de monitoreo las 24 horas\n" +
                "- Protocolo de evacuación inmediata en caso de fuga\n" +
                "- Vehículos blindados para los visitantes";
    }

    /**
     * Método específico de la clase TRex
     * 
     * @return Descripción del comportamiento territorial
     */
    public String defenderTerritorio() {
        if (alfaDeLaManada) {
            return getNombre() + " ruge ferozmente y marca su territorio. " +
                    "Como alfa, no tolera intrusos en su territorio.";
        } else {
            return getNombre() + " muestra comportamiento territorial, " +
                    "pero cede ante dinosaurios más dominantes.";
        }
    }

    /**
     * Obtiene la fuerza de mordida
     * 
     * @return Fuerza de mordida en kilonewtons
     */
    public int getFuerzaMordida() {
        return fuerzaMordida;
    }

    /**
     * Verifica si es el alfa de la manada
     * 
     * @return true si es el alfa, false en caso contrario
     */
    public boolean isAlfaDeLaManada() {
        return alfaDeLaManada;
    }

    /**
     * Establece si es el alfa de la manada
     * 
     * @param alfaDeLaManada Nuevo estado de dominancia
     */
    public void setAlfaDeLaManada(boolean alfaDeLaManada) {
        this.alfaDeLaManada = alfaDeLaManada;
    }

    @Override
    public String obtenerInformacion() {
        return super.obtenerInformacion() + "\n" +
                "Fuerza de mordida: " + fuerzaMordida + " kilonewtons\n" +
                "Estatus en la manada: " + (alfaDeLaManada ? "Alfa dominante" : "Subordinado");
    }
}