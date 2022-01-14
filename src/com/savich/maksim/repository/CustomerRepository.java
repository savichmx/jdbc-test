package com.savich.maksim.repository;

import com.savich.maksim.model.Customer;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;

public class CustomerRepository {

    private static CustomerRepository instance;

    private CustomerRepository() {}

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public Customer addCustomer(Customer customer) throws SQLException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("INSERT INTO customers(name, surname, age, address, date_of_birth) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, customer.getName());
            prepareStatement.setString(2, customer.getSurname());
            prepareStatement.setInt(3, customer.getAge());
            prepareStatement.setString(4, customer.getAddress());
            prepareStatement.setDate(5, Date.valueOf(customer.getDateOfBirth()));
            prepareStatement.execute();

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return customer;
        }
    }

    public Customer getById(int id) throws SQLException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id, name, surname, age, address, date_of_birth FROM customers WHERE id = ?")) {
            prepareStatement.setInt(1, id);
            ResultSet result = prepareStatement.executeQuery();
            Customer customer = null;
            if (result.next()) {
                customer = new Customer();
                customer.setId(result.getInt("id"));
                customer.setName(result.getString("name"));
                customer.setSurname(result.getString("surname"));
                customer.setAge(result.getInt("age"));
                customer.setAddress(result.getString("address"));
                customer.setDateOfBirth(Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
            }
            return customer;
        }
    }
}
