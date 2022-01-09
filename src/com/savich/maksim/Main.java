package com.savich.maksim;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
       CustomerRepository.getInstance().addCustomer("Tom", "Wilson", 23);
        CustomerRepository.getInstance().addCustomer("Tom", "Wilson", 23);
    }

}
