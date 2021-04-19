package com.sprint.challenge2.dtos.Flight;

import com.sprint.challenge2.dtos.StatusCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFlightDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private FlightReservationReturnedDTO flightReservation;
    private StatusCodeDTO statusCode;
}
