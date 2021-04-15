package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private String code;
    private String name;
    private String city;
    private String roomType;
    private Double nightPrice;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateFrom;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateTo;
    private Boolean booked;

}
