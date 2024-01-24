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
                            "Krakow",
                            "Mallorca",
                            LocalDate.of(2023, Month.NOVEMBER,10),
                            LocalDate.of(2023,Month.NOVEMBER,17),
                            0.00,
                            "ABC 123 11",
                            0.00,
                            "hello1",
                            0.00);

            Destination destination2 = new Destination(
                            "Katowice",
                             "Madrid",
                             LocalDate.of(2024, Month.JANUARY,15),
                             LocalDate.of(2024,Month.JANUARY,19),
                    0.00,
                    "CDE 456 22",
                    0.00,
                    "hello2",
                    0.00);

                     repository.saveAll(
                             List.of(destination1, destination2)
                     );
        };
    }
}
