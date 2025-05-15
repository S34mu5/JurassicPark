package jurassicpark.modelo.gestion;

/**
 * Clase que representa un cliente que realiza reservas.
 */
public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;

    /**
     * Constructor de la clase Cliente
     * 
     * @param id       Identificador único del cliente
     * @param nombre   Nombre del cliente
     * @param apellido Apellido del cliente
     * @param email    Email del cliente
     * @param telefono Teléfono del cliente
     */
    public Cliente(int id, String nombre, String apellido, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido +
                ", email=" + email + ", telefono=" + telefono + "}";
    }
}