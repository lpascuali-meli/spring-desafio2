package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.Hotel.HotelDTO;
import com.sprint.challenge2.dtos.Hotel.HotelFilterDTO;
import com.sprint.challenge2.dtos.Hotel.RequestBookingDTO;
import com.sprint.challenge2.dtos.Hotel.ResponseBookingDTO;
import com.sprint.challenge2.exceptions.ApiException;

import java.util.List;

public interface HotelService {

    public List<HotelDTO> getAllHotels() throws ApiException;
    public List<HotelDTO> getHotelsByFilters(HotelFilterDTO filters) throws ApiException;

    public ResponseBookingDTO createBookingHotel(RequestBookingDTO requestBookingDTO) throws ApiException;
}
