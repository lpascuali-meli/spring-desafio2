package com.sprint.challenge2.services;

import com.sprint.challenge2.dtos.HotelDTO;
import com.sprint.challenge2.exceptions.ApiException;
import com.sprint.challenge2.repositories.HotelRepository;
import com.sprint.challenge2.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateServiceImpl implements ValidateService {

    private final HotelRepository hotelRepository;
    // private FlightRepository flightRepository;

    public ValidateServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    /**
     * Validate the date format (dd/mm/yyyy)
     *
     * @param dateToValidate date with format (dd/mm/yyyy)
     * @return new Date if valid
     * @throws ApiException the Date is not valid
     */
    @Override
    public Date validateDateFormat(String dateToValidate) throws ApiException {
        Date date = null;
        String format = "dd/MM/yyyy";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateToValidate);
            if (!dateToValidate.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The date " + dateToValidate + " must have the format " + format);
        }
        return date;
    }

    /**
     * Validate that the dateFrom is previous that dateTo
     *
     * @param dateFrom previous date
     * @param dateTo   afterward date
     * @throws ApiException invalid range
     */
    @Override
    public void validateDateInterval(Date dateFrom, Date dateTo) throws ApiException {
        if (dateFrom.compareTo(dateTo) >= 0) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The date from must have previous to date to");
        }
    }

    /**
     * Validate than room type is one of allowed, and matches with the hotel room type
     * @param roomType room type of the request
     * @param hotelCode code of hotel to compare
     * @throws ApiException when the hotel was not found
     */
    @Override
    public void validateRoomType(String roomType, String hotelCode) throws ApiException {
        HotelDTO hotel = this.hotelRepository.getHotelByCode(hotelCode);
        boolean exist = false;
        if (hotel != null && hotel.getRoomType().toUpperCase(Locale.ROOT).equals(roomType.toUpperCase(Locale.ROOT))) {
            exist = true;
        }
        if (hotel == null || !exist)
            throw new ApiException(HttpStatus.BAD_REQUEST, "The hotel room type " + roomType + " doesn't exist for the hotel code " + hotelCode);
    }

    /**
     * Validate the existence of the destination
     * @param destination destination
     * @throws ApiException when the city does not exists
     */
    @Override
    public void validateHotelDestination(String destination, String hotelCode) throws ApiException {
        List<HotelDTO> hotels = this.hotelRepository.getAllHotels();
        boolean exist = false;
        for (HotelDTO hotel : hotels) {
            if (hotel.getCode().toUpperCase(Locale.ROOT).equals(hotelCode.toUpperCase(Locale.ROOT))
                && StringUtils.normalize(hotel.getCity()).equals(StringUtils.normalize(destination))) {
                exist = true;
                break;
            }
        }
        if (!exist)
            throw new ApiException(HttpStatus.BAD_REQUEST, "The hotel destination " + destination + " doesn't exist for the hotel code " + hotelCode);
    }

    /**
     * Validate that the person amount is equals or smaller that the capacity of the room type
     *
     * @param roomType room type
     * @param personAmount person amount
     * @throws ApiException when personAmount is higher than room capacity
     */
    @Override
    public void validatePersonAmount(String roomType, Integer personAmount) throws ApiException {
        String opt = roomType.toUpperCase(Locale.ROOT);
        switch (opt) {
            case "SINGLE":
                if (personAmount != 1)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "For " + roomType + " room type the amount of people allow is 1");
                break;
            case "DOBLE":
                if (personAmount != 2)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "For " + roomType + " room type the amount of people allow is 2");
                break;
            case "TRIPLE":
                if (personAmount != 3)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "For " + roomType + " room type the amount of people allow is 3");
                break;
            case "MULTIPLE":
                if (personAmount > 10 || personAmount < 4)
                    throw new ApiException(HttpStatus.BAD_REQUEST, "For " + roomType + " room type the amount of people allow is 4 - 10");
                break;
            default:
                throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid person amount");
        }
    }

    /**
     * Validate that the object passed is numeric type
     * @param object object type to validate
     * @param parameterName name of the parameter from the exception message
     * @throws ApiException when parameter is not numeric
     */
    @Override
    public void validateNumericType(Object object, String parameterName) throws ApiException {
        if (!(object instanceof Integer)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "The parameter " + parameterName + " have to be integer type");
        }
    }

    /**
     * Validate the email pattern
     * @param email email to validate
     * @throws ApiException when email is not valid
     */
    @Override
    public void validateEmailFormat(String email) throws ApiException {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid email " + email);
    }

    /**
     * Validate if the hotel is already booked
     * @param hotelCode hotel code
     */
    @Override
    public void validateFreeHotel(String hotelCode) throws ApiException {
        HotelDTO hotel = this.hotelRepository.getHotelByCode(hotelCode);
        if (Boolean.TRUE.equals(hotel.getBooked()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "The hotel " + hotel.getName() + " is already booked");
    }

    /**
     * Validate that the payment type is a available payment method
     * @param paymentType payment type
     */
    @Override
    public void validatePaymentType(String paymentType) throws ApiException {
        String pm = StringUtils.normalize(paymentType.toUpperCase(Locale.ROOT));
        if ((!pm.equals("CREDIT")) && (!pm.equals("DEBIT")))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Payment method " + paymentType + " not valid");
    }

    /**
     * Validate if paymentType is DEBIT, the due's quantity be 1
     * @param paymentType payment type
     * @param dues quantity of dues
     */
    @Override
    public void validatePaymentTypeWithDues(String paymentType, int dues) throws ApiException {
        String pm = StringUtils.normalize(paymentType.toUpperCase(Locale.ROOT));
        if (pm.equals("DEBIT") && dues > 1)
            throw new ApiException(HttpStatus.BAD_REQUEST, "Payment method DEBIT allows one due only");
    }

    /**
     * Validate the amount of dues
     * @param dues dues
     */
    @Override
    public void validateDuesAmount(Integer dues) throws ApiException {
        if (dues > 12 || dues < 1) throw new ApiException(HttpStatus.BAD_REQUEST, "Dues " + dues + " not valid");
    }

    /**
     * Validate that city already exists
     * @param destination name of hotel's city
     */
    @Override
    public void validateExistingDestination(String destination) throws ApiException {
        if(!this.hotelRepository.getAllDestinations().contains(StringUtils.normalize(destination)))  {
            throw new ApiException(HttpStatus.NOT_FOUND, "Destination " + destination + " doesn't exist");
        }
    }

    /*
    @Override
    public void validateFlightDestination(String destination, String flightNumber) throws ApiException {
        List<FlightDTO> flights = this.flightRepository.getAllFlights();
        boolean exist = false;
        for (FlightDTO flight : flights) {
            if (flight.getFlightNumber().toUpperCase(Locale.ROOT).equals(flightNumber.toUpperCase(Locale.ROOT)))
            if (StringUtils.normalize(flight.getDestination()).equals(StringUtils.normalize(destination))) {
                exist = true;
                break;
            }
        }
        if (!exist)
            throw new ApiException(HttpStatus.BAD_REQUEST, "The flight destination " + destination + " doesn't exist for the flight number " + flightNumber);
    }
*/
    /**
     * Validate the existence of the origin
     *
     * @param origin origin
     * @throws ApiException
     */
    /*
    @Override
    public void validateFlightOrigin(String origin, String flightNumber) throws ApiException {
        List<FlightDTO> flights = this.flightRepository.getAllFlights();
        boolean exist = false;
        for (FlightDTO flight : flights) {
            if (flight.getFlightNumber().toUpperCase(Locale.ROOT).equals(flightNumber.toUpperCase(Locale.ROOT)))
                if (StringUtils.normalize(flight.getOrigin()).equals(StringUtils.normalize(origin))) {
                exist = true;
                break;
            }
        }
        if (!exist) throw new ApiException(HttpStatus.BAD_REQUEST, "The flight origin " + origin + " doesn't exist for the flight number " + flightNumber);
    }
    */

}
