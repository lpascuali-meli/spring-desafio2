package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.Flight.*;
import com.sprint.challenge2.dtos.PaymentMethodDTO;
import com.sprint.challenge2.dtos.PersonDTO;
import com.sprint.challenge2.dtos.StatusCodeDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.FlightRepository;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilghtServiceImpl implements FlightService{
    private FlightRepository flightRepository;
    private ValidateService validateService;
    public FilghtServiceImpl(FlightRepository flightRepository, ValidateService validateService) {
        this.flightRepository = flightRepository;
        this.validateService = validateService;
    }

    @Override
    public List<FlightDTO> getAllFlights() throws ApiException {
        return flightRepository.getAllFlights();
    }

    @Override
    public List<FlightDTO> getFlightsByFilters(FlightFilterDTO filters) throws ApiException {
        if (filters != null) {
            // If there are filters, validate them
            validateFilters(filters);
        }
        // Get all flights
        List<FlightDTO> allFlights = getAllFlights();
        if (filters != null) {
            // If there are filters, select the flights which match
            allFlights = allFlights.stream()
                    .filter(flight -> {
                        boolean matches = true;
                        matches = !flight.getBooked();
                        if (filters.getOrigin() != null) {
                            matches = matches && StringUtils.normalize(flight.getOrigin()).equals(StringUtils.normalize(filters.getOrigin()));
                        }
                        if (filters.getDestination() != null) {
                            matches = matches && StringUtils.normalize(flight.getDestination()).equals(StringUtils.normalize(filters.getDestination()));
                        }
                        if (filters.getDateFrom() != null) {
                            matches = matches && filters.getDateFrom().compareTo(flight.getDateFrom()) >= 0;
                        }
                        if (filters.getDateTo() != null) {
                            matches = matches && filters.getDateTo().compareTo(flight.getDateTo()) <= 0;
                        }
                        return matches;
                    }).collect(Collectors.toList());
        }
        if (!allFlights.isEmpty()) {
            // If one or more flights found, return them
            return allFlights;
        }
        // If no flight match, return 404 status
        throw new ApiException(HttpStatus.NOT_FOUND, "Not flights found.");
    }

    /**
     * Method to validate flight search filters
     * @param filters FlightFilterDTO
     * @throws ApiException when some filter is wrong
     */
    public void validateFilters(FlightFilterDTO filters) throws ApiException {
        if (filters.getDateFrom() != null && filters.getDateTo() != null) {
            validateService.validateDateInterval(filters.getDateFrom(), filters.getDateTo());
        }
        if (filters.getOrigin() != null) {
            validateService.validateExistingFlightOrigin(filters.getOrigin());
        }
        if (filters.getDestination() != null) {
            validateService.validateExistingFlightDestination(filters.getDestination());
        }
    }

    /**
     * reserve some flight making all validations
     * @param requestReservationDTO the reservation to create
     * @return The reservation created
     * @throws ApiException when some data is wrong
     */
    @Override
    public ResponseFlightDTO createReservation(RequestFlightDTO requestReservationDTO) throws ApiException {
        validateRequestReservation(requestReservationDTO);
        FlightDTO flightFound = flightRepository.getFlightByCodeAndSeatType(requestReservationDTO.getFlightReservation().getFlightNumber(), requestReservationDTO.getFlightReservation().getSeatType());
        ResponseFlightDTO response = new ResponseFlightDTO();
        response.setUserName(requestReservationDTO.getUserName());
        response.setFlightReservation(new FlightReservationReturnedDTO(requestReservationDTO.getFlightReservation()));
        response.setAmount(calculateAmount(requestReservationDTO, flightFound));
        response.setInterest(calculateInterest(response.getAmount(), requestReservationDTO.getFlightReservation().getPaymentMethod()));
        response.setTotal(response.getAmount() + response.getInterest());
        response.setStatusCode(new StatusCodeDTO(200, "Booking successful"));
        flightRepository.reserveFlight(flightFound);
        return response;
    }

    /**
     * Validate all the fields of a new flight reservation
     * @param request reservation to create
     * @throws ApiException when some field is wrong
     */
    private void validateRequestReservation(RequestFlightDTO request) throws ApiException {
        FlightReservationDTO reservation = request.getFlightReservation();
        PaymentMethodDTO paymentMethod = request.getFlightReservation().getPaymentMethod();
        validateService.validateFreeFlight(reservation.getFlightNumber(), reservation.getSeatType());
        Date dateValidFrom = validateService.validateDateFormat(request.getFlightReservation().getDateFrom());
        Date dateValidTo = validateService.validateDateFormat(request.getFlightReservation().getDateTo());
        validateService.validateDateInterval(dateValidFrom, dateValidTo);
        validateService.validateExistingFlightOrigin(reservation.getOrigin());
        validateService.validateExistingFlightDestination(reservation.getDestination());
        validateService.validateEmailFormat(request.getUserName());
        for (PersonDTO person : reservation.getPeople()) {
            validateService.validateDateFormat(person.getBirthDate());
            validateService.validateEmailFormat(person.getMail());
        }
        validateService.validatePaymentType(paymentMethod.getType());
        validateService.validatePaymentTypeWithDues(paymentMethod.getType(), paymentMethod.getDues());
        validateService.validateDuesAmount(paymentMethod.getDues());
    }

    /**
     * Calculate the interest of a booking
     * @param amount price of the entire booking
     * @param paymentMethod cardType and dues
     * @return The interest calculated
     */
    private Double calculateInterest(Double amount, PaymentMethodDTO paymentMethod) {
        double interest = 0;
        if (paymentMethod.getType().equals("CREDIT")) {
            if (paymentMethod.getDues() <= 3) {
                interest = 0.05;
            } else if (paymentMethod.getDues() <= 6) {
                interest = 0.1;
            } else {
                interest = 0.15;
            }
        }
        return amount * interest;
    }

    /**
     * Calculate the price the reservation
     * @param requestFlightDTO The reservation request
     * @param flightDto The flight to reserve
     * @return Product between the price of flight and the people amount
     * @throws ApiException when date are wrong
     */
    private Double calculateAmount(RequestFlightDTO requestFlightDTO, FlightDTO flightDto) {
        return requestFlightDTO.getFlightReservation().getSeats() * flightDto.getPrice();
    }
}
