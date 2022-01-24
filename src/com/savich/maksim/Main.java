package com.savich.maksim;

import com.savich.maksim.model.Customer;
import com.savich.maksim.model.Payment;
import com.savich.maksim.repository.ConnectionHolder;
import com.savich.maksim.repository.CustomerRepository;
import com.savich.maksim.repository.PaymentRepository;
import com.savich.maksim.service.CustomerService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException {

        try {
            ConnectionHolder.getConnection().setAutoCommit(false);

            Payment payment = new Payment();
            payment.setAmount(new BigDecimal("44"));
            payment.setCurrency("RUB");

            PaymentRepository.getInstance().createPayment(payment);

            Customer customer = new Customer();
            customer.setName("Maksim");
            customer.setSurname("Savich");
            customer.setAge(17);
            customer.setAddress("Proletarskaya street 14/81");
            customer.setDateOfBirth(LocalDate.of(1994, 10, 1));

            CustomerRepository.getInstance().addCustomer(customer);

            if (customer != null) {
                throw new Exception("test");
            }

            ConnectionHolder.getConnection().commit();
        } catch (Exception e) {
            ConnectionHolder.getConnection().rollback();
        } finally {
            ConnectionHolder.getConnection().setAutoCommit(true);
        }
    }

}
