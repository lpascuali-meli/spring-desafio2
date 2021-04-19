package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HotelDtoTestHelper {

    public static List<HotelDTO> getTestHotels() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/testHotels.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static List<HotelDTO> getTestHotelsExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/GetHotels/testHotelsExpected.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static List<HotelDTO> getHotelsByCityExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/GetHotels/testHotelsByCityExpected.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static List<HotelDTO> getHotelsByDatesExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/GetHotels/testHotelsByDatesExpected.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static List<HotelDTO> getHotelsByAllFiltersExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/GetHotels/testHotelsByAllFiltersExpected.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static ResponseBookingDTO getBookingResponseExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/Booking/bookingResponseOK.json"), new TypeReference<ResponseBookingDTO>() {
        });
    }

    public static RequestBookingDTO getBookingOKRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/Booking/bookingRequestOk.json"), new TypeReference<RequestBookingDTO>() {
        });
    }

    public static HotelDTO getHotelByCode() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/Booking/hotelByCode.json"), new TypeReference<HotelDTO>() {
        });
    }

    public static List<String> getDestinations() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/Hotel/Booking/hotelDestinations.json"), new TypeReference<List<String>>() {
        });
    }
}
