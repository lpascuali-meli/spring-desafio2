package com.sprint.challenge2.repositories;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.exceptions.ApiException;

import java.util.List;

public interface HotelRepository {
    List<HotelDTO> getAllHotels() throws ApiException;
    HotelDTO getHotelByCode(String code) throws ApiException;
    List<String> getAllDestinations() throws ApiException;
    void bookHotel(HotelDTO hotelToBook) throws ApiException;
}
