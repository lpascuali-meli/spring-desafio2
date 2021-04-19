package com.sprint.challenge2.controllers;

import com.sprint.challenge2.dtos.Flight.*;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.services.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {
    private final FlightService flightService;
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    /**
     * Endpoint to search the available flights, using optional filters
     * @param filters DateFrom, DateTo, origin, destination
     * @return ResponseEntity<List<FlightDTO>>  List of the flights matched.
     * @throws ApiException When filters are wrong, or the city doesn't exist.
     */
    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights(FlightFilterDTO filters) throws ApiException {
        List<FlightDTO> flightsResult = flightService.getFlightsByFilters(filters);
        return new ResponseEntity<>(flightsResult, HttpStatus.OK);
    }


    /**
     * Endpoint to reserve some flight for people
     * @param requestReservationDTO the booking to process
     * @return The reservation created
     */
    @PostMapping(value = "/flight-reservation")
    public ResponseEntity<ResponseFlightDTO> createReservation(@RequestBody RequestFlightDTO requestReservationDTO) throws ApiException {
        ResponseFlightDTO reservationResult = flightService.createReservation(requestReservationDTO);
        return new ResponseEntity<>(reservationResult, HttpStatus.OK);
    }

}
