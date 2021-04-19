package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.*;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Filter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Unit Test | Hotel Service")
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
    @DisplayName("Get all hotels OK")
    void getHotels() throws IOException, ApiException {
        when(hotelRepository.getAllHotels()).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getTestHotelsExpected();
        List<HotelDTO> hotelsFound = hotelService.getHotelsByFilters(new HotelFilterDTO());

        verify(hotelRepository, atLeast(1)).getAllHotels();
        assertThat(hotelsFound).isEqualTo(hotelsExpected);
    }

    @Test
    @DisplayName("get Hotels for city Medellin")
    void getHotelsByCity() throws IOException, ApiException {
        when(hotelRepository.getAllHotels()).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getHotelsByCityExpected();
        HotelFilterDTO filters = new HotelFilterDTO();
        filters.setCity("Medellín");
        List<HotelDTO> hotelsFound = hotelService.getHotelsByFilters(filters);

        verify(hotelRepository, atLeast(1)).getAllHotels();
        assertThat(hotelsFound).isEqualTo(hotelsExpected);
    }

    @Test
    @DisplayName("get Hotes available into a date range")
    void getHotelsByDates() throws IOException, ApiException {
        when(hotelRepository.getAllHotels()).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getHotelsByDatesExpected();
        HotelFilterDTO filters = new HotelFilterDTO();
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(2021, Calendar.MARCH, 22);
        Calendar dateTo = Calendar.getInstance();
        dateTo.set(2021, Calendar.MARCH, 23);
        filters.setDateFrom(dateFrom.getTime());
        filters.setDateTo(dateTo.getTime());
        List<HotelDTO> hotelsFound = hotelService.getHotelsByFilters(filters);

        verify(hotelRepository, atLeast(1)).getAllHotels();
        assertThat(hotelsExpected).isEqualTo(hotelsFound);
    }

    @Test
    @DisplayName("get Hotels filtered by city and dates")
    void getHotelsByAllFilters() throws IOException, ApiException {
        when(hotelRepository.getAllHotels()).thenReturn(HotelDtoTestHelper.getTestHotels());

        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getHotelsByAllFiltersExpected();
        HotelFilterDTO filters = new HotelFilterDTO();
        Calendar dateFrom = Calendar.getInstance();
        dateFrom.set(2021, Calendar.MARCH, 22);
        Calendar dateTo = Calendar.getInstance();
        dateTo.set(2021, Calendar.MARCH, 23);
        filters.setCity("Medellín");
        filters.setDateFrom(dateFrom.getTime());
        filters.setDateTo(dateTo.getTime());
        List<HotelDTO> hotelsFound = hotelService.getHotelsByFilters(filters);

        verify(hotelRepository, atLeast(1)).getAllHotels();
        assertThat(hotelsExpected).isEqualTo(hotelsFound);
    }

    @DisplayName("Return ApiException when with not city found")
    @Test
    void validateNotExistingCIty() {
        HotelFilterDTO filters = new HotelFilterDTO();
        filters.setCity("Rafaela");
        assertThrows(ApiException.class, () -> hotelService.getHotelsByFilters(filters));
    }

    @DisplayName("Create booking OK")
    @Test
    void createBookingOk() throws ApiException, IOException {
        when(hotelRepository.getHotelByCode("CH-0003")).thenReturn(HotelDtoTestHelper.getHotelByCode());

        ResponseBookingDTO responseExpected = HotelDtoTestHelper.getBookingResponseExpected();
        RequestBookingDTO requestBookingDTO = HotelDtoTestHelper.getBookingOKRequest();
        ResponseBookingDTO responseResult = hotelService.createBookingHotel(requestBookingDTO);

        verify(hotelRepository, atLeast(1)).getHotelByCode("CH-0003");
        assertThat(responseResult).isEqualTo(responseExpected);
    }
}