package com.PlanYourHolidays.BestValueAlgorithm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BestValue {
    public static List<LocalDate> algorithm(String departureDate){

        LocalDate localDepartureDate = LocalDate.parse(departureDate);

        List<LocalDate> travelDepartureDates = new ArrayList<>();
        List<LocalDate> travelReturnDates = new ArrayList<>();

        for (int i = -3; i <= 3; i++) {
            travelDepartureDates.add(localDepartureDate.plusDays(i));
        }

        Collections.sort(travelDepartureDates);
        Collections.sort(travelReturnDates);

        travelDepartureDates.addAll(travelReturnDates);

//        Print the sorted dates
        for (LocalDate date : travelDepartureDates) {
            System.out.println(date);
        }
//        for (LocalDate date : travelReturnDates) {
//            System.out.println(date);
//        }


        return travelDepartureDates;

    }
}
