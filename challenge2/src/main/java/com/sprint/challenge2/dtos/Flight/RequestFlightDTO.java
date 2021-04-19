package com.sprint.challenge2.dtos.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestFlightDTO {
    private String userName;
    private FlightReservationDTO flightReservation;
}
