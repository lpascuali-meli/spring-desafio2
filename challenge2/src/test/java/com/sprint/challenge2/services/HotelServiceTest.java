package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.dtos.HotelDtoTestHelper;
import com.sprint.challenge2.dtos.HotelFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private ValidateService validateService;

    HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelServiceImpl(hotelRepository, validateService);
    }

    @Test
    @DisplayName("getAllProducts")
    void getHotels() throws IOException, ApiException {
        when(hotelRepository.getAllHotels()).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getTestHotelsExpected();
        List<HotelDTO> hotelsFound = hotelService.getHotelsByFilters(new HotelFilterDTO());

        verify(hotelRepository, atLeast(1)).getAllHotels();
        assertThat(hotelsFound).isEqualTo(hotelsExpected);
    }
}