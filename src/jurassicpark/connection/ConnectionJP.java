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
        String PORT = "3310"; // 
        String DATABASE = "jp_db";
        String USER = "root";
        String PASSWORD = "root";

      
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useUnicode=true&characterEncoding=utf-8";

      
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}