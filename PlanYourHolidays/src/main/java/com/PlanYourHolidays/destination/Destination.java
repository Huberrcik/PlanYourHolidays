package com.PlanYourHolidays.destination;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table
public class Destination {
    @Id
    @SequenceGenerator(
            name = "destinations_sequence",
            sequenceName =  "destinations_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "destinations_sequence"
    )
    private Long Id;
    private String startPoint;
    private String destinationPoint;
    private LocalDate dateOfStart;
    private LocalDate dateOfFinish;

    public Destination() {
    }

    public Destination(Long id,
                       String startPoint,
                       String destinationPoint,
                       LocalDate dateOfStart,
                       LocalDate dateOfFinish) {
        this.Id = id;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }

    public Destination(String startPoint,
                       String destinationPoint,
                       LocalDate dateOfStart,
                       LocalDate dateOfFinish) {
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.dateOfStart = dateOfStart;
        this.dateOfFinish = dateOfFinish;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + Id +
                ", startPoint='" + startPoint + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", dateOfStart=" + dateOfStart +
                ", dateOfFinish=" + dateOfFinish +
                '}';
    }
}
