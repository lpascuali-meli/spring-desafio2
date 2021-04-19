package com.sprint.challenge2.dtos.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBookingDTO {
    private String userName;
    private BookingDTO booking;
}
