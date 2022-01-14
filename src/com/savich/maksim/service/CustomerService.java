package com.savich.maksim.service;

import com.savich.maksim.model.Customer;
import com.savich.maksim.model.Payment;
import com.savich.maksim.repository.CustomerRepository;
import com.savich.maksim.repository.PaymentRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public class CustomerService {

    private static CustomerService instance;

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public Customer saveCustomer(Customer customer) throws SQLException {
        return CustomerRepository.getInstance().addCustomer(customer);
    }

    public Payment makePayment(Customer customer, BigDecimal amount, String currency) throws SQLException {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setCustomer(customer);
        return PaymentRepository.getInstance().createPayment(payment);
    }

}
