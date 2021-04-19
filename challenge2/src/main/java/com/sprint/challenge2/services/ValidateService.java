package com.sprint.challenge2.services;

import com.sprint.challenge2.exceptions.ApiException;

import java.util.Date;

public interface ValidateService {
    Date validateDateFormat(String date) throws ApiException;
    void validateDateInterval(Date dateFrom, Date dateTo) throws ApiException;
    void validateRoomType(String roomType, String hotelCode) throws ApiException;
    void validateHotelDestination(String destination, String hotelCode) throws ApiException;
    void validateNumericType(Object object, String parameterName) throws ApiException;
    void validatePersonAmount(String roomType, Integer personAmount) throws ApiException;
    void validateEmailFormat(String email) throws ApiException;
    void validateFreeHotel(String hotelCode) throws ApiException;
    void validatePaymentType(String type) throws ApiException;
    void validatePaymentTypeWithDues(String paymentType, int dues) throws ApiException;
    void validateDuesAmount(Integer dues) throws ApiException;
    void validateExistingDestination(String destination) throws ApiException;
    void validateFreeFlight(String flightNumber, String seatType) throws ApiException;
    void validateExistingFlightOrigin(String origin) throws ApiException;
    void validateExistingFlightDestination(String destination) throws ApiException;

}
