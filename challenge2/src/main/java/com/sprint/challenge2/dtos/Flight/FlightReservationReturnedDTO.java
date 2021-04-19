package com.sprint.challenge2.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprint.challenge2.dtos.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FlightReservationReturnedDTO {
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    List<PersonDTO> people;

    public FlightReservationReturnedDTO(FlightReservationDTO flightReservation) {
        dateFrom = flightReservation.getDateFrom();
        dateTo = flightReservation.getDateTo();
        origin = flightReservation.getOrigin();
        destination = flightReservation.getDestination();
        flightNumber = flightReservation.getFlightNumber();
        seats = flightReservation.getSeats();
        seatType = flightReservation.getSeatType();
        people = flightReservation.getPeople();
    }
}
