package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HotelDtoTestHelper {

    public static List<HotelDTO> getTestHotels() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/testHotels.json"), new TypeReference<List<HotelDTO>>() {
        });
    }

    public static List<HotelDTO> getTestHotelsExpected() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("src/main/resources/Test/testHotelsExpected.json"), new TypeReference<List<HotelDTO>>() {
        });
    }
}
