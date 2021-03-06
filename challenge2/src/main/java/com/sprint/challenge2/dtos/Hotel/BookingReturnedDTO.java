package com.sprint.challenge2.dtos.Hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprint.challenge2.dtos.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingReturnedDTO {
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateTo;
    private String destination;
    private String hotelCode;
    private Integer peopleAmount;
    private String roomType;
    List<PersonDTO> people;

    public BookingReturnedDTO(BookingDTO booking) {
        dateFrom = booking.getDateFrom();
        dateTo = booking.getDateTo();
        destination = booking.getDestination();
        hotelCode = booking.getHotelCode();
        peopleAmount = booking.getPeopleAmount();
        roomType = booking.getRoomType();
        people = booking.getPeople();
    }
}