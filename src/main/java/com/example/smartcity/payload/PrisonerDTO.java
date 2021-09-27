package com.example.smartcity.payload;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class PrisonerDTO {

    private long cardNumber;

    private String prisonDuration;

    private Date startingDate;

    private Date endingDate;

    private List<UUID> crimes;

}
