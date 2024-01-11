package com.PlanYourHolidays.gettingData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.PlanYourHolidays.gettingData.GettingListOfHotels.getHotelList;
import static com.PlanYourHolidays.gettingData.extractingDataFromEndpoint.getStringResponseEntity;

public class GettingBookings {
    private static final String URL = "https://test.api.amadeus.com/v3/shopping/hotel-offers?hotelIds=";
    private static final String adultsURL = "&adults=";
    private static final String checkInDateURL = "&checkInDate=";
    private static final String checkOutDateURL = "&checkOutDate=";
    private static final String roomQuantityURL = "&roomQuantity=";
    private static final String endpointURL = "&currency=PLN&paymentPolicy=NONE&includeClosed=false&bestRateOnly=true";

    public static double getHotelPrice(String destination, int radius, int hotelRating, int numberOfAdults, String checkInDate, String checkOutDate, int numberOfRooms) throws JSONException {

        List<String> hotelIds = getHotelList(destination,radius,hotelRating);

        String joinedString = String.join(", ", hotelIds);

        String finalURL = URL + joinedString + adultsURL + numberOfAdults + checkInDateURL + checkInDate + checkOutDateURL + checkOutDate +
                roomQuantityURL + numberOfRooms +endpointURL;

        String jsonResponse = String.valueOf(getStringResponseEntity(finalURL));
        System.out.println(jsonResponse);
        if(Objects.equals(jsonResponse, "<200 OK OK,0,[]>")){
            return 0;
        }

        int startIndex = jsonResponse.indexOf("{");
        String jsonData = jsonResponse.substring(startIndex);

        System.out.println(jsonData);


        JSONObject data = new JSONObject(jsonData);
        JSONArray dataArray = data.getJSONArray("data");
        double[] totalValues = new double[dataArray.length()];
        double lowestValue;
        if (dataArray.length() == 0) {
            System.out.println("There are no available rooms");
            return 0;
        } else {

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject offer = dataArray.getJSONObject(i).getJSONArray("offers").getJSONObject(0);
                String total = offer.getJSONObject("price").getString("total");
                totalValues[i] = Double.parseDouble(total);
            }

            for (double value : totalValues) {
                System.out.println("Total: " + value);
            }
        }

        Arrays.sort(totalValues);
        lowestValue = totalValues[0];
        System.out.println("Lowest value = " + lowestValue);




        return lowestValue;
    }

}
