package com.PlanYourHolidays.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@PasswordMatches
public class CustomerDto {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    public CustomerDto() {
    }

    public CustomerDto(@NotNull String name, @NotNull String password, @NotNull String matchingPassword, @NotNull String email) {
        this.name = name;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer convertToCustomer() {
        return new Customer(this.getName(), this.getEmail(), this.getPassword());
    }
}

