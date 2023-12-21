package com.PlanYourHolidays.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner customerCommandLineRunner(
            CustomerRepository repository) {
        return  args -> {
            Customer jakub = new Customer(
                    "Jakub",
                    "jakub@gmail.com",
                    23,
                    "haslo"
            );
            Customer hubert = new Customer(
                    "Hubert",
                    "hubert@gmail.com",
                    23,
                    "password"
            );

            repository.saveAll(
                    List.of(jakub, hubert)
            );
        };
    }
}
