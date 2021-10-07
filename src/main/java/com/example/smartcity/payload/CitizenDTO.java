package com.example.smartcity.payload;

import lombok.Data;

import java.util.Date;

@Data
public class CitizenDTO {

    private Long cardNumber;

    private String firstName;

    private String surname;

    private Date birthDate;
}
