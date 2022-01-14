package com.savich.maksim.repository;

import com.savich.maksim.model.Payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            prepareStatement.setInt(3, payment.getCustomer().getId());
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

    public Payment getById(int id) throws SQLException {
        try (PreparedStatement prepareStatement = ConnectionHolder.getConnection().prepareStatement("SELECT id, amount, currency, customer_id FROM payments WHERE id = ?")) {
            prepareStatement.setInt(1, id);
            ResultSet result = prepareStatement.executeQuery();
            Payment payment = null;
            if (result.next()) {
                payment = new Payment();
                payment.setId(result.getInt("id"));
                payment.setAmount(result.getBigDecimal("amount"));
                payment.setCurrency(result.getString("currency"));
                payment.setCustomer(CustomerRepository.getInstance().getById(result.getInt("customer_id")));
            }
            return payment;
        }
    }
}
