package com.savich.maksim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRepository implements AutoCloseable {

    public static final String URL = "jdbc:mysql://database-1.cf7g410jr7vi.eu-central-1.rds.amazonaws.com:3306/jdbc_test";
    private static final String USER = "admin";
    private static final String PASSWORD = "1qaz2wsx";

    private Connection connection;

    private static CustomerRepository instance;

    private CustomerRepository() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public void addCustomer(String name, String surname, int age) throws SQLException {
        try (PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO customers(name, surname, age) VALUES(?, ?, ?)")) {
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, surname);
            prepareStatement.setInt(3, age);
            prepareStatement.execute();
        }
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
