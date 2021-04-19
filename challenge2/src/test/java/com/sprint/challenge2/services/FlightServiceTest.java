package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.*;
import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.dtos.Flight.FlightFilterDTO;
import com.sprint.challenge2.dtos.Flight.RequestFlightDTO;
import com.sprint.challenge2.dtos.Flight.ResponseFlightDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test | Flight Service")
class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private ValidateService validateService;

    FlightService flightService;

    @BeforeEach
    void setUp() {
        flightService = new FilghtServiceImpl(flightRepository, validateService);
    }

    @Test
    @DisplayName("Get all flights OK")
    void getFlights() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getTestFlightsExpected();
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(new FlightFilterDTO());

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsFound).isEqualTo(flightsExpected);
    }

    @Test
    @DisplayName("get Flights for city Medellin like origin")
    void getFlightsByOriginCity() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getFlightsByOriginDestinationExpected();
        FlightFilterDTO filters = new FlightFilterDTO();
        filters.setOrigin("Medellín");
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(filters);

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsFound).isEqualTo(flightsExpected);
    }

    @Test
    @DisplayName("get Flights for city Puerto Iguazú like destination")
    void getFlightsByDestinationCity() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        FlightFilterDTO filters = new FlightFilterDTO();
        filters.setDestination("Puerto Iguazú");
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(filters);

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsFound.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("get Flights for specific origin and destination")
    void getFlightsByDestinationAndOriginCity() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getFlightsByOriginDestinationExpected();
        FlightFilterDTO filters = new FlightFilterDTO();
        filters.setOrigin("Medellín");
        filters.setDestination("Puerto Iguazú");
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(filters);

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsFound).isEqualTo(flightsExpected);
    }

    @Test
    @DisplayName("get flights available into a date range")
    void getFlightsByDates() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getFlightsByOriginDestinationExpected();
        FlightFilterDTO filters = new FlightFilterDTO();
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(2021, Calendar.APRIL, 17);
        Calendar dateTo = Calendar.getInstance();
        dateTo.set(2021, Calendar.MAY, 1);
        filters.setDateFrom(dateFrom.getTime());
        filters.setDateTo(dateTo.getTime());
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(filters);

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsExpected).isEqualTo(flightsFound);
    }

    @Test
    @DisplayName("get Flights filtered by city and dates")
    void getFlightsByAllFilters() throws IOException, ApiException {
        when(flightRepository.getAllFlights()).thenReturn(FlightDtoTestHelper.getTestFlights());

        List<FlightDTO> flightsExpected = FlightDtoTestHelper.getFlightsByOriginDestinationExpected();
        FlightFilterDTO filters = new FlightFilterDTO();
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(2021, Calendar.APRIL, 17);
        Calendar dateTo = Calendar.getInstance();
        dateTo.set(2021, Calendar.MAY, 1);
        filters.setDateFrom(dateFrom.getTime());
        filters.setDateTo(dateTo.getTime());
        filters.setOrigin("Medellín");
        filters.setDestination("Puerto Iguazú");
        List<FlightDTO> flightsFound = flightService.getFlightsByFilters(filters);

        verify(flightRepository, atLeast(1)).getAllFlights();
        assertThat(flightsExpected).isEqualTo(flightsFound);
    }

    @DisplayName("Return ApiException when with not origin city found")
    @Test
    void validateNotExistingOriginCity() {
        FlightFilterDTO filters = new FlightFilterDTO();
        filters.setOrigin("Rafaela");
        assertThrows(ApiException.class, () -> flightService.getFlightsByFilters(filters));
    }

    @DisplayName("Return ApiException when with not destination city found")
    @Test
    void validateNotExistingDestinationCity() {
        FlightFilterDTO filters = new FlightFilterDTO();
        filters.setDestination("Rafaela");
        assertThrows(ApiException.class, () -> flightService.getFlightsByFilters(filters));
    }


    @DisplayName("Create reservation OK")
    @Test
    void createReservationOk() throws ApiException, IOException {
        when(flightRepository.getFlightByCodeAndSeatType("BAPI-1235", "Economy")).thenReturn(FlightDtoTestHelper.getFlightByCode());

        ResponseFlightDTO responseExpected = FlightDtoTestHelper.getReservationResponseExpected();
        RequestFlightDTO requestBookingDTO = FlightDtoTestHelper.getReservationOKRequest();
        ResponseFlightDTO responseResult = flightService.createReservation(requestBookingDTO);

        verify(flightRepository, atLeast(1)).getFlightByCodeAndSeatType("BAPI-1235", "Economy");
        assertThat(responseResult).isEqualTo(responseExpected);
    }
}