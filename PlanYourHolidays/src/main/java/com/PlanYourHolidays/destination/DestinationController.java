package com.PlanYourHolidays.destination;

import com.PlanYourHolidays.BestValueAlgorithm.BestFlightsValues;
import com.PlanYourHolidays.BestValueAlgorithm.BestValue;
import com.PlanYourHolidays.gettingData.GettingBookings;
import com.PlanYourHolidays.gettingData.GettingFlights;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping(path="api/v1/destination")
public class DestinationController {

    private final DestinationService destinationService;
    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }
    @GetMapping
    public List<Destination> getJourneys() {
        return destinationService.getJourneys();
    }

    @PostMapping
    public void searchNewDestination(@RequestBody Destination destination){
        destinationService.addNewDestination(destination);
    }
    @DeleteMapping(path = "{destinationId}")
    public void deleteDestination(@PathVariable("destinationId") Long destinationId){
        destinationService.deleteDestination(destinationId);
    }
    @PutMapping(path = "{destinationId}")
    public void updateDestination(@PathVariable("destinationId") Long destinationId,
                                  @RequestParam(required = false) String startingPoint,
                                  @RequestParam(required = false) String destinationPoint,
                                  @RequestParam(required = false) LocalDate dateOfStart,
                                  @RequestParam(required = false) LocalDate dateOfFinish,
                                  @RequestParam(required = false) float flightsPrice,
                                  @RequestParam(required = false) float sleepPrice,
                                  @RequestParam(required = false) float bestTotalPrice) {
        destinationService.upadteDestination(destinationId, startingPoint, destinationPoint, dateOfStart, dateOfFinish, flightsPrice, sleepPrice, bestTotalPrice);

    }
    @GetMapping("/flightsData&{destinationId}&{flightTo}&{flightFrom}&{departureDate}&{returnDate}&{seats}&{radius}&{hotelRating}&{numberOfRooms}")
    //  /flightsData&{1}&{PMI}&{KRK}&{2024-02-10}&{2024-02-17}&{1}&{50}&{4}&{1}
    // /flightsData&{1}&{1}&{50}&{4}&{1}
    public void callFlightsEndpoint(@PathVariable("destinationId") Long destinationId,
                                    @PathVariable String flightTo,
                                    @PathVariable String departureDate,
                                    @PathVariable String flightFrom,
                                    @PathVariable String returnDate,
                                    @PathVariable int seats,
                                    @PathVariable int radius,
                                    @PathVariable int hotelRating,
                                    @PathVariable int numberOfRooms,
                                    @RequestParam(required = false) String startingPoint,
                                    @RequestParam(required = false) String destinationPoint,
                                    @RequestParam(required = false) LocalDate dateOfStart,
                                    @RequestParam(required = false) LocalDate dateOfFinish
                                    ) throws JSONException {

        float sleepPrice = (float) GettingBookings.getHotelPrice(flightTo, radius, hotelRating, seats, departureDate, returnDate, numberOfRooms);
        float flightsPrice = (float) GettingFlights.getFlightData(flightTo, flightFrom, departureDate, returnDate, seats);
        float bestTotalPrice = BestFlightsValues.flightDeal(flightTo,flightFrom,BestValue.algorithm(departureDate),BestValue.algorithm(returnDate),seats,radius,hotelRating,numberOfRooms);
        destinationService.upadteDestination(destinationId, startingPoint, destinationPoint, dateOfStart, dateOfFinish, flightsPrice, sleepPrice, bestTotalPrice);
    }
}
