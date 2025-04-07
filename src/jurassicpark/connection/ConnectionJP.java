package jurassicpark.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión a la base de datos MySQL
 */
public class ConnectionJP {

    /**
     * Obtiene una conexión a la base de datos MySQL
     * 
     * @return Conexión a la base de datos
     * @throws SQLException si hay error en la conexión
     */
    public Connection getConnection() throws SQLException {
        try {
            // Cargar explícitamente el driver de MySQL. Da problemas.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("No se pudo cargar el driver de MySQL: " + e.getMessage());
        }

        // Parámetros de conexión
        String HOST = "localhost";
        String PORT = "3306"; // Cambia esto si tu servidor MySQL usa otro puerto
        String DATABASE = "jp_db"; // Nombre de tu base de datos
        String USER = "root";
        String PASSWORD = "root";

        // URL de conexión a MySQL con parámetros necesarios para Docker
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

        // Establecer conexión
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}