package com.PlanYourHolidays.BestValueAlgorithm;

import com.PlanYourHolidays.gettingData.GettingBookings;
import com.PlanYourHolidays.gettingData.GettingFlights;
import com.PlanYourHolidays.gettingData.extractingDataFromEndpoint;
import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class BestFlightsValues {

    static Logger log = Logger.getLogger(extractingDataFromEndpoint.class.getName());
    public static Map
            <String,
                    Object> flightDeal(String flightTo, String flightFrom, List<LocalDate> departureDates, List<LocalDate> returnDates, int seats, int radius, int hotelRating, int rooms) throws JSONException {
        List<Double> flightPrices = new ArrayList<>();
        List<Double> hotelPrices = new ArrayList<>();
        List<String> flightCode = new ArrayList<>();
        List<String> hotelName = new ArrayList<>();
        Double bestFlightPrice = null;
        Double bestHotelPrice = null;
        String bestFlightCode = null;
        String bestHotelName = null;

        for(int i = 0;i<departureDates.size();i++){
            flightPrices.add(GettingFlights.getFlightData(flightTo,
                    flightFrom ,
                    String.valueOf(departureDates.get(i)),
                    String.valueOf(returnDates.get(i)),
                    seats));
            flightCode.add(GettingFlights.getBestFlightCode(flightTo,
                    flightFrom ,
                    String.valueOf(departureDates.get(i)),
                    String.valueOf(returnDates.get(i)),
                    seats));
            hotelPrices.add(GettingBookings.getHotelPrice(flightTo,
                    radius ,
                    hotelRating,
                    seats,
                    String.valueOf(departureDates.get(i)),
                    String.valueOf(returnDates.get(i)),
                    rooms));
            hotelName.add(GettingBookings.getBestHotelName(flightTo,
                    radius ,
                    hotelRating,
                    seats,
                    String.valueOf(departureDates.get(i)),
                    String.valueOf(returnDates.get(i)),
                    rooms));
        }

        List<LocalDate> bestDepartureDates = new ArrayList<>();
        List<LocalDate> bestReturnDates = new ArrayList<>();
        double bestTotalPrice = Double.MAX_VALUE;

        for (int i = 0; i < flightPrices.size(); i++) {
            for (int j = 0; j < hotelPrices.size(); j++) {
                if (i < departureDates.size() && j < returnDates.size() && flightPrices.get(i) != 0 && hotelPrices.get(j) != 0) {
                    if (returnDates.get(j).isAfter(departureDates.get(i))) {
                        long diffInDays = Math.abs(returnDates.get(j).toEpochDay() - departureDates.get(i).toEpochDay());
                        double totalPrice = flightPrices.get(i) + hotelPrices.get(j);

                        if (diffInDays >= 3 && diffInDays <= 14 && totalPrice < bestTotalPrice) {
                            bestTotalPrice = totalPrice;
                            bestFlightPrice = flightPrices.get(i);
                            bestFlightCode = flightCode.get(i);
                            bestHotelPrice = hotelPrices.get(j);
                            bestHotelName = hotelName.get(j);
                            bestDepartureDates.clear();
                            bestReturnDates.clear();
                            bestDepartureDates.add(departureDates.get(i));
                            bestReturnDates.add(returnDates.get(j));
                            log.info(String.valueOf(bestTotalPrice));
                            log.info(String.valueOf(bestDepartureDates.add(departureDates.get(i))));
                            log.info(String.valueOf(bestReturnDates.add(returnDates.get(j))));
                        }
                    }
                }
            }
        }

        // Print the best combination of dates and total price
        Map<String, Object> resultMap = new HashMap<>();
        if (!bestDepartureDates.isEmpty() && !bestReturnDates.isEmpty()) {
            resultMap.put("bestDepartureDate", bestDepartureDates.get(0));
            resultMap.put("bestReturnDate", bestReturnDates.get(0));
            resultMap.put("bestTotalPrice", bestTotalPrice);
            resultMap.put("bestFlightPrice", bestFlightPrice);
            resultMap.put("flightCode",bestFlightCode);
            resultMap.put("bestHotelPrice", bestHotelPrice);
            resultMap.put("hotelName", bestHotelName);
        } else {
            log.info("Could not find suitable prices");
            resultMap.put("bestDepartureDate", null);
            resultMap.put("bestReturnDate", null);
            resultMap.put("bestTotalPrice", 0.0);
            resultMap.put("bestFlightPrice", 0.0);
            resultMap.put("flightCode",null);
            resultMap.put("bestHotelPrice", 0.0);
            resultMap.put("hotelName", null);
        }

        return resultMap;
    }
}
