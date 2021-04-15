package com.sprint.challenge2.controllers;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.dtos.HotelFilterDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("")
    public ResponseEntity<List<HotelDTO>> getHotels(HotelFilterDTO filters) throws ApiException {
        List<HotelDTO> hotelsResult = hotelService.getHotelsByFilters(filters);
        return new ResponseEntity<>(hotelsResult, HttpStatus.OK);
    }
}
