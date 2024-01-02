package com.PlanYourHolidays.BestValueAlgorithm;

import com.PlanYourHolidays.gettingData.GettingBookings;
import com.PlanYourHolidays.gettingData.GettingFlights;
import org.json.JSONException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BestFlightsValues {
    public static float flightDeal(String flightTo, String flightFrom, List<LocalDate> departureDates, List<LocalDate> returnDates, int seats, int radius, int hotelRating, int rooms) throws JSONException {
        List<Double> flightPrices = new ArrayList<>();
        List<Double> hotelPrices = new ArrayList<>();

        for(int i = 0;i<departureDates.size();i++){
            flightPrices.add(GettingFlights.getFlightData(flightTo,
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
                            bestDepartureDates.clear();
                            bestReturnDates.clear();
                            bestDepartureDates.add(departureDates.get(i));
                            bestReturnDates.add(returnDates.get(j));
                        }
                    }
                }
            }
        }

        // Print the best combination of dates and total price
        if (!bestDepartureDates.isEmpty() && !bestReturnDates.isEmpty()) {
            System.out.println("Best departure date: " + bestDepartureDates.get(0));
            System.out.println("Best return date: " + bestReturnDates.get(0));
            System.out.println("Best total price: " + bestTotalPrice);
        } else {
            System.out.println("No suitable combination found.");
        }


        return (float) bestTotalPrice;
    }
}
