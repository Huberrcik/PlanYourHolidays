package com.PlanYourHolidays.destination;

import com.PlanYourHolidays.gettingData.GettingFlights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                                  @RequestParam(required = false) float sleepPrice) {
        destinationService.upadteDestination(destinationId, startingPoint, destinationPoint, dateOfStart, dateOfFinish, flightsPrice, sleepPrice);

    }
    @GetMapping("/flightsData")
    public ResponseEntity<?> callFlightsEndpoint() {
        return ResponseEntity.ok(GettingFlights.getFlightData());
    }

}
