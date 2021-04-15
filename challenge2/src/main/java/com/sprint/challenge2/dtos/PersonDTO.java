package com.sprint.challenge2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String dni;
    private String name;
    private String lastname;
    private Date birthDate;
    private String email;
    
}
