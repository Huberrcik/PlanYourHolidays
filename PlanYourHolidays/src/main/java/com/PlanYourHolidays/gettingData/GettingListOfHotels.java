package com.PlanYourHolidays.gettingData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.PlanYourHolidays.gettingData.extractingDataFromEndpoint.getStringResponseEntity;


/*

Reference for this endpoint - https://developers.amadeus.com/self-service/category/hotels/api-doc/hotel-list/api-reference

 */
@Service
@Slf4j
public class GettingListOfHotels {

    private static final String URL = "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?";
    private static final String cityCodeURL = "cityCode=";
    private static final String radiusURL = "&radius=";
    private static final String radiusUnitURL = "radiusUnit=KM";
    private static final String ratingURL = "&ratings=";
    private static final String hotelSourceURL = "&hotelSource=ALL";
    public static List<String> getHotelList(String cityCode, int radius, int hotelRating) {

        String finalURL = URL + cityCodeURL + cityCode + radiusURL + radius + radiusUnitURL + radiusURL + ratingURL + hotelRating + hotelSourceURL;

        String jsonResponse = String.valueOf(getStringResponseEntity(finalURL));

        int startIndex = jsonResponse.indexOf("{");
        String jsonOnly = jsonResponse.substring(startIndex);

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> hotelIds = new ArrayList<>();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonOnly);
            JsonNode dataNode = jsonNode.get("data");

            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    String hotelId = node.get("hotelId").asText();
                    hotelIds.add(hotelId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        System.out.println(hotelIds);

        return hotelIds;


    }


}
