package com.savich.maksim.model;

import java.time.LocalDate;

import com.savich.maksim.annotation.Column;

public class Customer {

    @Column("id")
    private Integer id;

    @Column("name")
    private String name;

    @Column("surname")
    private String surname;

    @Column("age")
    private Integer age;

    @Column("address")
    private String address;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format("Customer: id=%d, name=%s, surname=%s, age=%d, address=%s, dateOfBirth=%s", id, name, surname, age, address, dateOfBirth);
    }

}
