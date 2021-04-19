package com.sprint.challenge2.dtos.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightFilterDTO {
    private String origin;
    private String destination;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dateFrom;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dateTo;
}
