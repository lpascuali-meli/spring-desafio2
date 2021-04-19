package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.dtos.Flight.RequestFlightDTO;
import com.sprint.challenge2.dtos.Flight.ResponseFlightDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FlightDtoTestHelper {

    public static List<FlightDTO> getTestFlights() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/testFlights.json"), new TypeReference<List<FlightDTO>>() {
        });
    }

    public static List<FlightDTO> getTestFlightsExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/GetFlights/flightsExpected.json"), new TypeReference<List<FlightDTO>>() {
        });
    }

    public static List<FlightDTO> getFlightsByOriginDestinationExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/GetFlights/flightsByOriginDestinationExpected.json"), new TypeReference<List<FlightDTO>>() {
        });
    }

    public static FlightDTO getFlightByCode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/Reservation/flightByCode.json"), new TypeReference<FlightDTO>() {
        });
    }

    public static ResponseFlightDTO getReservationResponseExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/Reservation/reservationExpected.json"), new TypeReference<ResponseFlightDTO>() {
        });
    }

    public static RequestFlightDTO getReservationOKRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Flight/Reservation/reservationRequest.json"), new TypeReference<RequestFlightDTO>() {
        });
    }
}
