package com.sprint.challenge2.controllers;

import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.dtos.FlightDtoTestHelper;
import com.sprint.challenge2.dtos.Flight.FlightFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test | Flight Controller")
class FlightControllerTest {
    @Mock
    private FlightService flightService;

    FlightController flightController;

    @BeforeEach
    void setUp() {
        flightController = new FlightController(flightService);
    }

    @Test
    @DisplayName("getAllFlights")
    void getFlights() throws IOException, ApiException {
        when(flightService.getFlightsByFilters(any())).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getTestFlights();
        ResponseEntity<List<FlightDTO>> responseExpected = new ResponseEntity<>(flightsExpected, HttpStatus.OK);
        ResponseEntity<List<FlightDTO>> responseFound = flightController.getFlights(new FlightFilterDTO());

        verify(flightService, atLeast(1)).getFlightsByFilters(any());
        assertThat(responseFound).isEqualTo(responseExpected);
    }
}