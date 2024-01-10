package com.PlanYourHolidays.destination;

import com.PlanYourHolidays.BestValueAlgorithm.BestFlightsValues;
import com.PlanYourHolidays.BestValueAlgorithm.BestValue;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowedHeaders = "*", allowCredentials = "true")
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
        destinationService.updteDestination(destinationId, startingPoint, destinationPoint, dateOfStart, dateOfFinish, flightsPrice, sleepPrice, bestTotalPrice);

    }
    @GetMapping("/flightsData&{flightTo}&{flightFrom}&{departureDate}&{returnDate}&{seats}&{radius}&{hotelRating}&{numberOfRooms}")
    public String callFlightsEndpoint(
                                    @PathVariable String flightTo,
                                    @PathVariable String departureDate,
                                    @PathVariable String flightFrom,
                                    @PathVariable String returnDate,
                                    @PathVariable int seats,
                                    @PathVariable int radius,
                                    @PathVariable int hotelRating,
                                    @PathVariable int numberOfRooms
                                    ) throws JSONException {

        Map<String, Object> result = BestFlightsValues.flightDeal(flightTo, flightFrom, BestValue.algorithm(departureDate), BestValue.algorithm(returnDate), seats, radius, hotelRating, numberOfRooms);
        LocalDate bestDepartureDate = (LocalDate) result.get("bestDepartureDate");
        LocalDate bestReturnDate = (LocalDate) result.get("bestReturnDate");
        double bestTotalPrice = (double) BestFlightsValues.flightDeal(flightTo,flightFrom,BestValue.algorithm(departureDate),BestValue.algorithm(returnDate),seats,radius,hotelRating,numberOfRooms).get("bestTotalPrice");
        Destination destination = new Destination(flightTo,flightFrom, bestDepartureDate, bestReturnDate, result.get("bestHotelPrice"), result.get("bestFlightPrice"), bestTotalPrice);
        destinationService.addNewDestination(destination);

        return destinationService.getJourneys().get(getJourneys().size() - 1).toString();
    }
}
