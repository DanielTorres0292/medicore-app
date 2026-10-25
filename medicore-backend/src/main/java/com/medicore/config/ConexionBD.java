package com.medicore.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Se agrega 'allowPublicKeyRetrieval=true' a la URL
    private static final String URL = "jdbc:mysql://localhost:3306/medicore_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456"; // Tu contraseña de MySQL

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado: " + e.getMessage());
        }
    }
}