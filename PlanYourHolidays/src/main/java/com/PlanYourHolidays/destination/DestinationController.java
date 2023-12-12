package com.PlanYourHolidays.destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
