package com.PlanYourHolidays.customer;

import jakarta.persistence.*;

@Entity
@Table
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customers_sequence",
            sequenceName =  "customers_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customers_sequence"
    )
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String password;

    public Customer() {
    }

    public Customer(Long id,
                    String name,
                    String email,
                    Integer age,
                    String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public Customer(String name,
                    String email,
                    Integer age,
                    String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }
}
