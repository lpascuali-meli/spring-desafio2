package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String dateTo;
    private String destination;
    private String hotelCode;
    private Integer peopleAmount;
    private String roomType;
    List<PersonDTO> people;
    PaymentMethodDTO paymentMethod;

}