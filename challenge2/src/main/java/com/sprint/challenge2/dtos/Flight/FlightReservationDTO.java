package com.sprint.challenge2.dtos.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprint.challenge2.dtos.PaymentMethodDTO;
import com.sprint.challenge2.dtos.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private String dateFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private String dateTo;
    private String origin;
    private String destination;
    private String flightNumber;
    private Integer seats;
    private String seatType;
    List<PersonDTO> people;
    PaymentMethodDTO paymentMethod;
}
