/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jurassicpark.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hayun
 */
public class ConnectionJP {

    public Connection getConnection() throws SQLException {
        String HOST = "localhost";
        String PORT = "3310";
        String DATABASE = "jp_db";
        String USER = "root";
        String PASSWORD = "root";
        String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
