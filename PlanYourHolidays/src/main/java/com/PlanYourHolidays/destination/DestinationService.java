package com.PlanYourHolidays.destination;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
        destinationRepository.save(destination);
        System.out.println(destination);
    }

    public void deleteDestination(Long destinationId) {
        boolean exists = destinationRepository.existsById(destinationId);

        if(!exists){
            throw new IllegalStateException("Destination with id " + destinationId + " does not exists");
        }
        destinationRepository.deleteById(destinationId);
    }

    @Transactional
    public void updteDestination(Long destinationId,
                                  String startPoint,
                                  String destinationPoint,
                                  LocalDate dateOfStart,
                                  LocalDate dateOfFinish,
                                  float flightsPrice,
                                  float sleepPrice,
                                  float bestTotalPrice) {
        Destination destination = destinationRepository.findDestinationById(destinationId).
                orElseThrow(() -> new IllegalStateException(
                "Destination with id " + destinationId + " does not exists"
        ));


        if (startPoint != null && !startPoint.isEmpty() && !Objects.equals(destination.getStartPoint(), startPoint)){
            destination.setStartPoint(startPoint);
        }

        if (destinationPoint != null && !destinationPoint.isEmpty() && !Objects.equals(destination.getDestinationPoint(), destinationPoint)){
            destination.setStartPoint(startPoint);
        }

        if (dateOfStart != null && !Objects.equals(destination.getDateOfStart(), dateOfStart)){
            destination.setDateOfStart(dateOfStart);
        }

        if (dateOfFinish != null && !Objects.equals(destination.getDateOfFinish(), dateOfFinish)){
            destination.setDateOfFinish(dateOfFinish);
        }

        if (flightsPrice != 0 && !Objects.equals(destination.getFlightsPrice(), flightsPrice)){
            destination.setFlightsPrice(flightsPrice);
        }

        if (sleepPrice != 0 && !Objects.equals(destination.getSleepPrice(), sleepPrice)){
            destination.setSleepPrice(sleepPrice);
        }

        if (bestTotalPrice != 0 && !Objects.equals(destination.getBestTotalPrice(), bestTotalPrice)){
            destination.setBestTotalPrice(bestTotalPrice);
        }


    }
}
