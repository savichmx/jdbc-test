package com.savich.maksim.repository;

import com.savich.maksim.annotation.Column;
import com.savich.maksim.model.Customer;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

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
        String query = String.format("INSERT INTO customers(%s) VALUES(?, ?, ?, ?, ?)", getAllColumns());
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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

    public Optional<Customer> getById2(int id) throws SQLException, IllegalAccessException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id, name, surname, age, address, date_of_birth FROM customers WHERE id = ?")) {
            prepareStatement.setInt(1, id);
            ResultSet result = prepareStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapResultSetToCustomer(result));
            }
            return Optional.empty();
        }
    }

    public Optional<Customer> getById(int id) throws SQLException, IllegalAccessException {
        String query = String.format("SELECT %s FROM customers WHERE id = ?", getAllColumns());
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, id);
            ResultSet result = prepareStatement.executeQuery();

            if (result.next()) {
                return Optional.of(mapResultSetToCustomer(result));
            }
            return Optional.empty();
        }
    }

    private Customer mapResultSetToCustomer(ResultSet result) throws SQLException, IllegalAccessException {
        Customer customer = new Customer();
        for (Field field : Customer.class.getDeclaredFields()) {

            if (field.isAnnotationPresent(Column.class)) {

                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                String columnName = column.value();
                Object value = null;

                if (field.getGenericType() == String.class) {
                    value = result.getString(columnName);
                } else if (field.getGenericType() == Integer.class) {
                    value = result.getInt(columnName);
                } else if (field.getGenericType() == LocalDate.class) {
                    value = Instant.ofEpochMilli(result.getDate(columnName).getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                }

                field.set(customer, value);
            }
        }
        return customer;
    }

    private String getAllColumns() {
        StringBuilder result = new StringBuilder();
        Field[] fields = Customer.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Column.class)) {
                Column column = fields[i].getAnnotation(Column.class);
                String columnName = column.value();
                result.append(columnName);
                if (i != fields.length - 1) {
                    result.append(", ");
                }
            }
        }
        return result.toString();
    }
}
