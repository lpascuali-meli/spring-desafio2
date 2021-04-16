package com.sprint.challenge2.controllers;

import com.sprint.challenge2.dtos.*;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Endpoint to search the available hotels, using optional filters
     * @param filters City, DateFrom, DateTo
     * @return ResponseEntity<List<HotelDTO>>  List of the hotels matched.
     * @throws ApiException When filters are wrong, or the city doesn't exist.
     */
    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDTO>> getHotels(HotelFilterDTO filters) throws ApiException {
        List<HotelDTO> hotelsResult = hotelService.getHotelsByFilters(filters);
        return new ResponseEntity<>(hotelsResult, HttpStatus.OK);
    }

    /**
     * Endpoint to booking some room for people
     * @param requestBookingDTO the booking to process
     * @return The booking created
     */
    @PostMapping(value = "/booking")
    public ResponseEntity<ResponseBookingDTO> createBookingHotel(@RequestBody RequestBookingDTO requestBookingDTO) throws ApiException {
        ResponseBookingDTO bookingResult = hotelService.createBookingHotel(requestBookingDTO);
        return new ResponseEntity<>(bookingResult, HttpStatus.OK);
    }
}
