package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.Flight.*;
import com.sprint.challenge2.exceptions.ApiException;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getAllFlights() throws ApiException;
    List<FlightDTO> getFlightsByFilters(FlightFilterDTO filters) throws ApiException;
    ResponseFlightDTO createReservation(RequestFlightDTO requestReservationDTO) throws ApiException;
}
