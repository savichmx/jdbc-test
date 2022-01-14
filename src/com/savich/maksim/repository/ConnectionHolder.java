package com.savich.maksim.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHolder implements AutoCloseable {

    public static final String URL = "jdbc:mysql://database-1.cf7g410jr7vi.eu-central-1.rds.amazonaws.com:3306/jdbc_test";
    private static final String USER = "admin";
    private static final String PASSWORD = "1qaz2wsx";

    private static Connection connection;

    private ConnectionHolder() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

}
