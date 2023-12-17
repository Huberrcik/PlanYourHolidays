package com.PlanYourHolidays.scraping;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Month;

public class GettingFlights {
    final String URL = "https://www.google.com/travel/flights?q=Flights";
    String flightTo = "";
    String flightFrom = "";
    LocalDate departureDate = LocalDate.of(2024, Month.JANUARY,15);
    LocalDate arrivalDate = LocalDate.of(2024,Month.JANUARY,19);

    int seats = 2;

}
