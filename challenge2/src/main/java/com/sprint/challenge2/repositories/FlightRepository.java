package com.sprint.challenge2.repositories;

import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.exceptions.ApiException;

import java.util.List;

public interface FlightRepository {
    List<FlightDTO> getAllFlights() throws ApiException;

    List<String> getAllOrigins() throws ApiException;

    List<String> getAllDestinations() throws ApiException;

    FlightDTO getFlightByCodeAndSeatType(String flightNumber, String seatType) throws ApiException;

    void reserveFlight(FlightDTO flightFound) throws ApiException;
}
