package com.savich.maksim.model;

import java.math.BigDecimal;

public class Payment {

    private Integer id;
    private BigDecimal amount;
    private String currency;
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return String.format("Payment: amount=%s, currency=%s, customerName=%s", amount, currency, customer.getName() + " " + customer.getSurname());
    }
}
