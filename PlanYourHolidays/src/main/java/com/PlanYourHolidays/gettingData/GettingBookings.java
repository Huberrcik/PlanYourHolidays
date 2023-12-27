package com.PlanYourHolidays.gettingData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import static com.PlanYourHolidays.gettingData.extractingDataFromEndpoint.getStringResponseEntity;


/*

Reference for this endpoint - https://developers.amadeus.com/self-service/category/hotels/api-doc/hotel-list/api-reference

 */
@Service
@Slf4j
public class GettingBookings {

    private static final String URL = "https://test.api.amadeus.com/v1/reference-data/locations/hotels/by-city?";
    private static final String cityCodeURL = "cityCode=";
    private static final String radiusURL = "&radius=";
    private static final String radiusUnitURL = "radiusUnit=KM";
    private static final String ratingURL = "&ratings=";
    private static final String hotelSourceURL = "&hotelSource=ALL";
    public static ResponseEntity<String> getHotelData(){

        String cityCode = "KRK";
        int radius = 50; //radius around airport that that will be searched
        int hotelRating = 2;
        String finalURL = URL + cityCodeURL + cityCode + radiusURL + radius + radiusUnitURL + radiusURL + ratingURL + hotelRating + hotelSourceURL;

        log.info("Sent request to: " + finalURL);
        return getStringResponseEntity(finalURL);


    }


}
