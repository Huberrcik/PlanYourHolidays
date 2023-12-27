package com.PlanYourHolidays.gettingData;

import java.util.logging.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static com.PlanYourHolidays.gettingData.Authorization.getAuth;

public class extractingDataFromEndpoint {
    public static ResponseEntity<String> getStringResponseEntity(String finalURL) {

        Logger logger = Logger.getLogger(extractingDataFromEndpoint.class.getName());

        logger.info("Sent request to: " + finalURL);
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAuth());

            HttpEntity<String> request = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();

            //System.out.println(response.getBody());

            return restTemplate.exchange(finalURL, HttpMethod.GET, request, String.class);

        } catch (Exception e) {
            logger.info(("Sth went wrong while getting value from API: " + e));
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Exception while calling external API",
                    e
            );
        }
    }
}