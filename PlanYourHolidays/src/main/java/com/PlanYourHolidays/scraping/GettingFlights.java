package com.PlanYourHolidays.scraping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;

@Service
@Slf4j
public class GettingFlights {
    private static final String URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=";
    static String flightTo = "SYD";
    private static final String destinationURL = "&destinationLocationCode=";
    static String flightFrom = "BKK";
    private static final String departureDateURL = "&departureDate=";
    static LocalDate departureDate = LocalDate.of(2024, Month.MAY,02);
    private static final String reurnDateURL = "&returnDate=";
    static LocalDate returnDate = LocalDate.of(2024,Month.JUNE,02);
    private static final String numberOfAdultsURL = "&adults=";
    static int seats = 2;
    private static final String nonStopURL = "&nonStop=";
    static boolean nonStop = false;

    static String accesToken = "XRz6jky9ZDdcILRuMkSegqYDwC8h";

    @Autowired
    private static RestTemplate restTemplate;

    public static Object getFlightData(){

        String finalURL = URL + flightTo +destinationURL + flightFrom +departureDateURL + departureDate + reurnDateURL + returnDate + numberOfAdultsURL + seats + nonStopURL + nonStop;
        log.info(finalURL);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization",accesToken);

            ResponseEntity<String> response = restTemplate.exchange(finalURL, HttpMethod.GET, new HttpEntity<>(headers), String.class);

            log.info("Output from endpoint: ",response.getBody());

            return response;
        }catch (Exception e){
            log.error("Sth went wrong while getting value from API");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling external API",
                    e
            );
        }
    }





}
