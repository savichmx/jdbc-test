package com.savich.maksim.repository;

import com.savich.maksim.model.Customer;
import com.savich.maksim.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

public class PaymentRepository {

    private static PaymentRepository instance;

    private PaymentRepository() {
    }

    public static PaymentRepository getInstance() {
        if (instance == null) {
            instance = new PaymentRepository();
        }
        return instance;
    }

    public Payment createPayment(Payment payment) throws SQLException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("INSERT INTO payments(amount, currency, customer_id) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setBigDecimal(1, payment.getAmount());
            prepareStatement.setString(2, payment.getCurrency());
            prepareStatement.setInt(3, payment.getCustomer() != null ? payment.getCustomer().getId() : 1);
            prepareStatement.execute();

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating payment failed, no ID obtained.");
                }
            }

            return payment;
        }
    }

    public Optional<Payment> getById(int id) throws SQLException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("SELECT p.id, p.amount, p.currency, p.customer_id, c.name, c.surname, " +
                                                                                                                "c.age, c.address, c.date_of_birth " +
                                                                                                            "FROM payments p " +
                                                                                                            "INNER JOIN customers c ON p.customer_id = c.id " +
                                                                                                        "WHERE p.id = ?")) {
            prepareStatement.setInt(1, id);
            ResultSet result = prepareStatement.executeQuery();
            if (result.next()) {
                Payment payment = new Payment();
                payment.setId(result.getInt("id"));
                payment.setAmount(result.getBigDecimal("amount"));
                payment.setCurrency(result.getString("currency"));
                if (result.getString("customer_id") != null) {
                    Customer customer = new Customer();
                    customer.setId((result.getInt("customer_id")));
                    customer.setName((result.getString("name")));
                    customer.setSurname((result.getString("surname")));
                    customer.setAge(result.getInt("age"));
                    customer.setAddress(result.getString("address"));
                    customer.setDateOfBirth(Instant.ofEpochMilli(result.getDate("date_of_birth").getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
                    payment.setCustomer(customer);
                }
                return Optional.of(payment);
            }
            return Optional.empty();
        }
    }
}
