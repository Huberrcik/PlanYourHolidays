package com.PlanYourHolidays.gettingData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;

import static com.PlanYourHolidays.gettingData.Authorization.getAuth;

@Service
@Slf4j
public class GettingFlights {
    private static final String URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=";




    static String flightTo = "SYD";
    private static final String destinationURL = "&destinationLocationCode=";
    static String flightFrom = "BKK";
    private static final String departureDateURL = "&departureDate=";
    static LocalDate departureDate = LocalDate.of(2024, Month.MAY, 2);
    private static final String reurnDateURL = "&returnDate=";
    static LocalDate returnDate = LocalDate.of(2024,Month.JUNE, 2);
    private static final String numberOfAdultsURL = "&adults=";
    static int seats = 2;
    private static final String nonStopURL = "&nonStop=";
    static boolean nonStop = false;


    public static ResponseEntity<String> getFlightData(){

        String finalURL = URL + flightTo +destinationURL + flightFrom +departureDateURL + departureDate + reurnDateURL + returnDate + numberOfAdultsURL + seats + nonStopURL + nonStop;
        log.info(finalURL);
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAuth());

            HttpEntity<String> request = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            //System.out.println(response.getBody());

            return restTemplate.exchange(finalURL, HttpMethod.GET, request, String.class);

        }catch (Exception e){
            log.error("Sth went wrong while getting value from API");
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling external API",
                    e
            );
        }
    }

    public static Object extracter() throws JsonProcessingException {
        String jsonResponse = String.valueOf(getFlightData());

        ObjectMapper mapper = new ObjectMapper();


            JsonNode root = mapper.readTree(jsonResponse);

            JsonNode priceNode = root.path("price");
            String currency = priceNode.get("currency").asText();
            String total = priceNode.get("total").asText();

            // Create a new JSON object with the extracted values
            ObjectMapper responseMapper = new ObjectMapper();
            ObjectNode responseObject = responseMapper.createObjectNode();
            responseObject.put("currency", currency);
            responseObject.put("total", total);

            // Convert the JSON object to a string

        return responseMapper.writeValueAsString(responseObject);

    }

}
