package com.savich.maksim;

import com.savich.maksim.model.Customer;
import com.savich.maksim.model.Payment;
import com.savich.maksim.repository.PaymentRepository;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws SQLException {
        /*Customer customer = CustomerRepository.getInstance().getById(7);
        Payment payment = CustomerService.getInstance().makePayment(customer, new BigDecimal("15.34"), "USD");*/
        Payment payment = PaymentRepository.getInstance().getById(2);
        System.out.println(payment);
    }

    private static void createCustomer() throws SQLException {
        Customer customer = new Customer();
        customer.setName("Mike");
        customer.setSurname("Wilson");
        customer.setAge(17);
        customer.setAddress("Proletarskaya street 14/81");
        customer.setDateOfBirth(LocalDate.of(1994, 10, 1));
    }

}
