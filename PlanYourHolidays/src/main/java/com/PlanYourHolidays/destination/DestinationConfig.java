package com.PlanYourHolidays.destination;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Configuration
public class DestinationConfig {
    @Bean
    CommandLineRunner commandLineRunner(DestinationRepository repository){
        return args ->{
                    Destination destination1 = new Destination(
                            "KRK",
                            "PMI",
                            LocalDate.of(2024, Month.FEBRUARY,10),
                            LocalDate.of(2024,Month.FEBRUARY,17));

            Destination destination2 = new Destination(
                            "Katowice",
                             "Madrid",
                             LocalDate.of(2024, Month.JANUARY,15),
                             LocalDate.of(2024,Month.JANUARY,19));

                     repository.saveAll(
                             List.of(destination1, destination2)
                     );
        };
    }
}
