package com.example.smartcity.payload;

import lombok.Data;

import java.util.Date;

@Data
public class PrisonerDTO {

    private long cardNumber;

    private String prisonDuration;

    private Date startingDate;

    private Date endingDate;
}
