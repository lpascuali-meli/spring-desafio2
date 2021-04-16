package com.sprint.challenge2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookingDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private BookingReturnedDTO booking;
    private StatusCodeDTO statusCode;
}
