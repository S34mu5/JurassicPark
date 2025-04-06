package jurassicpark.modelo.gestion;

/**
 * Clase que representa a un visitante que asiste al parque.
 */
public class Visitante {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String tipoVisitante; // "ADULTO", "NIÑO", "ADULTO_MAYOR"

    /**
     * Constructor de la clase Visitante
     * 
     * @param id       Identificador único del visitante
     * @param nombre   Nombre del visitante
     * @param apellido Apellido del visitante
     * @param edad     Edad del visitante
     */
    public Visitante(int id, String nombre, String apellido, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.asignarTipoVisitante();
    }

    /**
     * Asigna el tipo de visitante según la edad
     */
    private void asignarTipoVisitante() {
        if (edad < 12) {
            this.tipoVisitante = "NIÑO";
        } else if (edad >= 65) {
            this.tipoVisitante = "ADULTO_MAYOR";
        } else {
            this.tipoVisitante = "ADULTO";
        }
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
        asignarTipoVisitante();
    }

    public String getTipoVisitante() {
        return tipoVisitante;
    }

    @Override
    public String toString() {
        return "Visitante{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido +
                ", edad=" + edad + ", tipo=" + tipoVisitante + "}";
    }
}