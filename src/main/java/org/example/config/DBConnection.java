package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/jdbc_demo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Aman@15112003";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
