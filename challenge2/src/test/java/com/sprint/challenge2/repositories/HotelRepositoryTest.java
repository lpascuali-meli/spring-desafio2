package com.sprint.challenge2.repositories;

import com.sprint.challenge2.dtos.Hotel.HotelDTO;
import com.sprint.challenge2.dtos.HotelDtoTestHelper;
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
@DisplayName("Unit Test | Hotel Repository")
public class HotelRepositoryTest {

    HotelRepository hotelRepository;

    @BeforeEach
    void setUp() {
        hotelRepository = new HotelRepositoryImpl("src/main/resources/Test/Hotel/testHotels.json");
    }

    @Test
    @DisplayName("Get all hotels OK")
    void getHotels() throws IOException, ApiException {
        List<HotelDTO> hotelsExpected = HotelDtoTestHelper.getTestHotels();
        List<HotelDTO> hotelsFound = hotelRepository.getAllHotels();
        assertThat(hotelsFound).isEqualTo(hotelsExpected);
    }

    @Test
    @DisplayName("Get hotel by code OK")
    void getHotelsByCode() throws IOException, ApiException {
        HotelDTO hotelExpected = HotelDtoTestHelper.getHotelByCode();
        HotelDTO hotelFound = hotelRepository.getHotelByCode("CH-0003");
        HotelDTO hotelNotFound = hotelRepository.getHotelByCode("CH-01231003");
        assertThat(hotelFound).isEqualTo(hotelExpected);
        assertThat(hotelNotFound).isNull();
    }

    @Test
    @DisplayName("Get hotel by code OK")
    void getAllDestination() throws IOException, ApiException {
        List<String> destinationsExpected = HotelDtoTestHelper.getDestinations();
        List<String>  destinationsFound = hotelRepository.getAllDestinations();
        assertThat(destinationsExpected.size()).isEqualTo(destinationsFound.size());
    }
}
