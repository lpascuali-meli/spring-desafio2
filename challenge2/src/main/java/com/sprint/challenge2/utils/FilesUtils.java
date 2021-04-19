package com.sprint.challenge2.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprint.challenge2.dtos.Flight.FlightDTO;
import com.sprint.challenge2.dtos.Hotel.HotelDTO;
import com.sprint.challenge2.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesUtils {

    public static <T> List<T> getAllObjects(String filePath) throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<T> objList = null;
        try {
            objList = objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<T>>() {
            });
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
        return objList;
    }

    public static void writeFile(String filePath, List<HotelDTO> hotelDTOList) throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), hotelDTOList);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }

    public static void writeFileFlight(String filePath, List<FlightDTO> listUpdated) throws ApiException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(filePath), listUpdated);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
        }
    }
}
