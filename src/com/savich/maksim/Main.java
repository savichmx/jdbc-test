package com.savich.maksim;

import java.sql.SQLException;

import com.savich.maksim.model.Customer;
import com.savich.maksim.repository.CustomerRepository;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException {

        Customer customer = CustomerRepository.getInstance().getById(8).get();
        System.out.println(customer);
    }

}
