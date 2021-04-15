package com.sprint.challenge2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilterDTO {
    private String city;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dateFrom;
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dateTo;
}
