package com.sprint.challenge2.services;

import static org.junit.jupiter.api.Assertions.*;

import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import com.sprint.challenge2.repositories.HotelRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

@DisplayName("Unit Test | Validate Filter Service")
class ValidateFilterServiceImplTest {

    private ValidateService validateService;
    //private FlightRepository flightRepository;

    @BeforeEach
    void init() {
        HotelRepository hotelRepository = new HotelRepositoryImpl("src/main/resources/Test/testHotels.json");
        // flightRepository = new FlightRepositoryImpl("src/main/resources/dbFlights.json");
        validateService = new ValidateServiceImpl(hotelRepository);
    }

    @DisplayName("Check date with format dd/mm/yyyy")
    @Test
    void validateDateFormat_shouldReturnOk() throws ApiException {
        String dateToValidate = "21/07/1995";
        // Validate the date passed
        Date date = validateService.validateDateFormat(dateToValidate);
        assertNotNull(date);
    }

    @DisplayName("Return ApiException when with incorrect date format")
    @Test
    void validateInvalidDateFormat_shouldReturnApiException() {
        String invalidDate1 = "21.07-1995";
        String invalidDate2 = "21071995";
        String invalidDate3 = "21-07-1995";
        String invalidDate4 = "21//07//1995";
        String invalidDate5 = "akjsnd2.+";
        // Validate the date passed
        assertThrows(ApiException.class, () -> validateService.validateDateFormat(invalidDate1));
        assertThrows(ApiException.class, () -> validateService.validateDateFormat(invalidDate2));
        assertThrows(ApiException.class, () -> validateService.validateDateFormat(invalidDate3));
        assertThrows(ApiException.class, () -> validateService.validateDateFormat(invalidDate4));
        assertThrows(ApiException.class, () -> validateService.validateDateFormat(invalidDate5));
    }


    @DisplayName("Check dateFrom is previous to dateTo")
    @Test
    void validateIntervalDate_shouldReturnOk() throws ApiException {
        Date dateFrom = validateService.validateDateFormat("01/07/2021");
        Date dateTo = validateService.validateDateFormat("10/07/2021");
        // Validate the date passed
        assertDoesNotThrow(() -> validateService.validateDateInterval(dateFrom, dateTo));
    }

    @DisplayName("Return ApiException if the dateFrom isn't previous to dateTo")
    @Test
    void validateInvalidIntervalDate_shouldReturnApiException() throws ApiException {
        Date dateFrom = validateService.validateDateFormat("01/07/2021");
        Date dateTo = validateService.validateDateFormat("01/07/2021");
        // Validate the date passed
        assertThrows(ApiException.class, () -> validateService.validateDateInterval(dateFrom, dateTo));
    }

    @DisplayName("Check valid room type")
    @Test
    void checkValidRoomType_shouldReturnOK() {
        // Valid the single room
        assertDoesNotThrow(() -> validateService.validateRoomType("DOBLE", "SE-0002"));
    }

    @DisplayName("Return ApiException with invalid room type")
    @Test
    void checkInvalidRoomType_shouldReturnApiException() {
        // Check invalid room
        assertThrows(ApiException.class, () -> validateService.validateRoomType("TRIPLE", "SE-0002"));
    }

    @DisplayName("Check valid hotel destination")
    @Test
    void checkValidHotelDestination_shouldReturnOK() {
        assertDoesNotThrow(() -> validateService.validateHotelDestination("Bogota", "SE-0002"));
    }

    @DisplayName("Return ApiException if the hotel destination is invalid")
    @Test
    void checkInvalidHotelDestination_shouldReturnApiException() {
        assertThrows(ApiException.class, () -> validateService.validateHotelDestination("La Pampa", "SE-0002"));
    }

    /*@DisplayName("Check valid flight destination")
    @Test
    void checkValidFlightDestination_shouldReturnOK() {
        assertDoesNotThrow(() -> validateService.validateFlightDestination("Medellin", "BOME-4442"));
    }*/

    @DisplayName("Return ApiException if the flight destination is invalid")
    @Test
    void checkInvalidFlightDestination_shouldReturnApiException() {
        assertThrows(ApiException.class, () -> validateService.validateHotelDestination("La Pampa", "BOME-4442"));
    }

    /*
    @DisplayName("Check valid flight origin")
    @Test
    void checkValidFlightOrigin_shouldReturnOK() {
        assertDoesNotThrow(() -> validateService.validateFlightOrigin("Bogota", "BOME-4442"));
    }*/

    @DisplayName("Check valid numeric type")
    @Test
    void checkNumericType_shouldReturnOK() {
        Object personAmount = 123;
        assertDoesNotThrow(() -> validateService.validateNumericType(personAmount, "personAmount"));
    }

    @DisplayName("Return ApiException if the type isn't integer")
    @Test
    void checkInvalidNumericType_shouldReturnApiException() {
        Object personAmount = "123";
        assertThrows(ApiException.class, () -> validateService.validateNumericType(personAmount, "personAmount"));
    }

    @DisplayName("Check valid person amount for specific type room")
    @Test
    void checkPersonAmount_shouldReturnOK() {
        assertDoesNotThrow(() -> validateService.validatePersonAmount("SINGLE", 1));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("DOBLE", 2));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("TRIPLE", 3));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 4));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 5));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 6));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 7));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 8));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 9));
        assertDoesNotThrow(() -> validateService.validatePersonAmount("MULTIPLE", 10));
    }

    @DisplayName("Return ApiException if the person amount for the type room is invalid")
    @Test
    void checkinvalidPersonAmount_shouldReturnApiException() {
        assertThrows(ApiException.class, () -> validateService.validatePersonAmount("SINGLE", 2));
        assertThrows(ApiException.class, () -> validateService.validatePersonAmount("DOBLE", 3));
        assertThrows(ApiException.class, () -> validateService.validatePersonAmount("TRIPLE", 4));
        assertThrows(ApiException.class, () -> validateService.validatePersonAmount("MULTIPLE", 40));
        assertThrows(ApiException.class, () -> validateService.validatePersonAmount("MULTIPLE", 20));
    }

    @DisplayName("Check valid email")
    @Test
    void checkEmail_shouldReturnOK() {
        String email = "domain@domain.com";
        assertDoesNotThrow(() -> validateService.validateEmailFormat(email));
    }

    @DisplayName("Return ApiException if the email is invalid")
    @Test
    void checkInvalidEmail_shouldReturnApiException() {
        String e1 = "@domain.com";
        String e2 = "domain@.com";
        String e3 = "domain@domain";
        String e4 = "domain+domain.com";
        String e5 = "domain@domain.";
        String e6 = "domain@";
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e1));
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e2));
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e3));
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e4));
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e5));
        assertThrows(ApiException.class, () -> validateService.validateEmailFormat(e6));
    }
}
