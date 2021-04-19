package com.sprint.challenge2.repositories;

import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.dtos.FlightDtoTestHelper;
import com.sprint.challenge2.exceptions.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test | Flight Repository")
public class FlightRepositoryTest {

    FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository = new FlightRepositoryImpl("src/main/resources/Test/Flight/testFlights.json");
    }

    @Test
    @DisplayName("Get all flights OK")
    void getFlights() throws IOException, ApiException {
        List<FlightDTO> hotelsExpected = FlightDtoTestHelper.getTestFlights();
        List<FlightDTO> hotelsFound = flightRepository.getAllFlights();
        assertThat(hotelsFound).isEqualTo(hotelsExpected);
    }

}
