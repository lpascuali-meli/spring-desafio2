package com.sprint.challenge2.controllers;

import com.sprint.challenge2.dtos.Hotel.HotelDTO;
import com.sprint.challenge2.dtos.HotelDtoTestHelper;
import com.sprint.challenge2.dtos.Hotel.HotelFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.services.HotelService;
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
@DisplayName("Unit Test | Hotel Controller")
class HotelControllerTest {
    @Mock
    private HotelService hotelService;

    HotelController hotelController;

    @BeforeEach
    void setUp() {
        hotelController = new HotelController(hotelService);
    }

    @Test
    @DisplayName("getAllFlights")
    void getHotels() throws IOException, ApiException {
        when(hotelService.getHotelsByFilters(any())).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getTestHotels();
        ResponseEntity<List<HotelDTO>> responseExpected = new ResponseEntity<>(hotelsExpected, HttpStatus.OK);
        ResponseEntity<List<HotelDTO>> responseFound = hotelController.getHotels(new HotelFilterDTO());

        verify(hotelService, atLeast(1)).getHotelsByFilters(any());
        assertThat(responseFound).isEqualTo(responseExpected);
    }
}