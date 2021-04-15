package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.dtos.HotelFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;

import java.util.List;

public interface HotelService {

    public List<HotelDTO> getAllHotels() throws ApiException;
    public List<HotelDTO> getHotelsByFilters(HotelFilterDTO filters) throws ApiException;
}
