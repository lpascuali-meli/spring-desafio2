package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.*;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import com.sprint.challenge2.utils.DateUtils;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ValidateService validateService;

    public HotelServiceImpl(HotelRepository hotelRepository, ValidateService validateService) {
        this.validateService = validateService;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<HotelDTO> getAllHotels() throws ApiException {
        return hotelRepository.getAllHotels();
    }

    @Override
    public List<HotelDTO> getHotelsByFilters(HotelFilterDTO filters) throws ApiException {
        if (filters != null) {
            // If there are filters, validate them
            validateFilters(filters);
        }
        // Get all hotels
        List<HotelDTO> allHotels = getAllHotels();
        if (filters != null) {
            // If there are filters, select the hotels which match
            allHotels = allHotels.stream()
                .filter(hotel -> {
                    boolean matches = true;
                    matches = !hotel.getBooked();
                    if (filters.getCity() != null) {
                        matches = matches && StringUtils.normalize(hotel.getCity()).equals(StringUtils.normalize(filters.getCity()));
                    }
                    if (filters.getDateFrom() != null) {
                        matches = matches && filters.getDateFrom().compareTo(hotel.getDateFrom()) >= 0;
                    }
                    if (filters.getDateTo() != null) {
                        matches = matches && filters.getDateTo().compareTo(hotel.getDateTo()) <= 0;
                    }
                    return matches;
                }).collect(Collectors.toList());
        }
        if (!allHotels.isEmpty()) {
            // If one or more hotels found, return them
            return allHotels;
        }
        // If no hotel match, return 404 status
        throw new ApiException(HttpStatus.NOT_FOUND, "Not hotels found.");
    }

    /**
     * Method to validate hotel search filters
     * @param filters HotelFilterDto
     * @throws ApiException when some filter is wrong
     */
    public void validateFilters(HotelFilterDTO filters) throws ApiException {
        if (filters.getDateFrom() != null && filters.getDateTo() != null) {
            validateService.validateDateInterval(filters.getDateFrom(), filters.getDateTo());
        }
        if (filters.getCity() != null) {
            validateService.validateExistingDestination(filters.getCity());
        }
    }

    /**
     * Book some hotel room making all validations
     * @param requestBookingDTO the book to create
     * @return The booking creation
     * @throws ApiException when some data is wrong
     */
    @Override
    public ResponseBookingDTO createBookingHotel(RequestBookingDTO requestBookingDTO) throws ApiException {
        validateRequestBooking(requestBookingDTO);
        HotelDTO hotelFound = hotelRepository.getHotelByCode(requestBookingDTO.getBooking().getHotelCode());
        ResponseBookingDTO response = new ResponseBookingDTO();
        response.setUserName(requestBookingDTO.getUserName());
        response.setBooking(new BookingReturnedDTO(requestBookingDTO.getBooking()));
        response.setAmount(calculateAmount(requestBookingDTO, hotelFound));
        response.setInterest(calculateInterest(response.getAmount(), requestBookingDTO.getBooking().getPaymentMethod()));
        response.setTotal(response.getAmount() + response.getInterest());
        response.setStatusCode(new StatusCodeDTO(200, "Booking successful"));
        hotelRepository.bookHotel(hotelFound);
        return response;
    }

    /**
     * Validate all the fields of a new hotel booking
     * @param req booking to create
     * @throws ApiException when some field is wrong
     */
    public void validateRequestBooking(RequestBookingDTO req) throws ApiException {
        BookingDTO booking = req.getBooking();
        PaymentMethodDTO paymentMethod = req.getBooking().getPaymentMethod();
        validateService.validateFreeHotel(booking.getHotelCode());
        Date dateValidFrom = validateService.validateDateFormat(req.getBooking().getDateFrom());
        Date dateValidTo = validateService.validateDateFormat(req.getBooking().getDateTo());
        validateService.validateDateInterval(dateValidFrom, dateValidTo);
        validateService.validateHotelDestination(booking.getDestination(), booking.getHotelCode());
        validateService.validateRoomType(booking.getRoomType(), booking.getHotelCode());
        validateService.validatePersonAmount(booking.getRoomType(), booking.getPeopleAmount());
        validateService.validateEmailFormat(req.getUserName());
        for (PersonDTO person : booking.getPeople()) {
            validateService.validateDateFormat(person.getBirthDate());
            validateService.validateEmailFormat(person.getMail());
        }
        validateService.validatePaymentType(paymentMethod.getType());
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
     * Calculate the price of all nights of booking
     * @param requestBookingDTO The booking request
     * @param hotelDTO The hotel to book, with the night price
     * @return Product between night price and total nights of booking
     * @throws ApiException when date are wrong
     */
    private Double calculateAmount(RequestBookingDTO requestBookingDTO, HotelDTO hotelDTO) throws ApiException {
        int daysBetween = DateUtils.calculateDaysBetweenDates(requestBookingDTO.getBooking().getDateFrom(), requestBookingDTO.getBooking().getDateTo());
        return daysBetween * hotelDTO.getNightPrice();
    }
}
