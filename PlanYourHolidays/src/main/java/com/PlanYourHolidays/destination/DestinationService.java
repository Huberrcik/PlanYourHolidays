package com.PlanYourHolidays.destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;
    @Autowired
    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping
    public List<Destination> getJourneys(){
        return destinationRepository.findAll();
    }

    public void addNewDestination(Destination destination) {

        Optional<Destination> destinationOptional = destinationRepository.findDestinationById(destination.getId());

        if(destinationOptional.isPresent()){
            throw new IllegalStateException("id already exists in db");
        }
        System.out.println(destination);
    }
}
