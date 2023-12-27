package com.PlanYourHolidays.gettingData;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.PlanYourHolidays.gettingData.extractingDataFromEndpoint.getStringResponseEntity;

@Service
@Slf4j
public class GettingFlights {
    private static final String URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=";
    private static final String destinationURL = "&destinationLocationCode=";
    private static final String departureDateURL = "&departureDate=";
    private static final String reurnDateURL = "&returnDate=";
    private static final String numberOfAdultsURL = "&adults=";
    private static final String nonStopURL = "&nonStop=";
    private static final String currencyCodeURL = "&currencyCode=";
    private static final String maxPriceURL = "&maxPrice=";
    static private final String  maxResultsURL = "&max=";


    public static double getFlightData(String flightTo, String flightFrom, String departureDate, String returnDate, int seats) throws JSONException {

        //String flightTo = "JFK";
        //String flightFrom = "MLA";

        //String departureDate = "2024-05-02";
        //String returnDate = "2024-05-09";

        //int seats = 2;

        boolean nonStop = false;

        String currencyCode = "PLN";

        int maxPrice = 10000;

        int maxResults = 5;

        String finalURL = URL + flightTo +destinationURL + flightFrom +departureDateURL + departureDate + reurnDateURL
                + returnDate + numberOfAdultsURL + seats + nonStopURL + nonStop + currencyCodeURL + currencyCode
                + maxPriceURL + maxPrice + maxResultsURL + maxResults;

        String result = String.valueOf(getStringResponseEntity(finalURL));

        return extracter(result);
    }

    public static double extracter(String jsonResponse ) throws JSONException {

        System.out.println(jsonResponse);

        int startIndex = jsonResponse.indexOf("{");
        String jsonOnly = jsonResponse.substring(startIndex);

        JSONObject jsonObject = new JSONObject(jsonOnly);
        //JSONArray dataArray = jsonObject.getJSONArray("data");
        //String price = dataArray.getJSONObject(4).getJSONObject("price").getString("total");
        JSONArray data = jsonObject.getJSONArray("data");
        //System.out.println(price);
        List<Double> totalValues = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject flightOffer = data.getJSONObject(i);
            JSONObject price = flightOffer.getJSONObject("price");
            String total = price.getString("total");
            System.out.println("Total " + (i + 1) + ": " + total);
            totalValues.add(Double.parseDouble(total));
        }

        double lowestValue = Collections.min(totalValues);
        System.out.println("Lowest total value: " + lowestValue);

        return lowestValue;
    }

}
