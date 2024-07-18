package com.madhav.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {

    private static final String URL = "jdbc:postgresql://localhost:5432/invoice_application";
    private static final String USER = "postgres";
    private static final String PASSWORD = "latha";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
