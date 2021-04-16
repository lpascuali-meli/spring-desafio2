package com.sprint.challenge2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private String dni;
    private String name;
    private String lastname;
    @JsonFormat(pattern="dd/MM/yyyy")
    private String birthDate;
    private String mail;
    
}
