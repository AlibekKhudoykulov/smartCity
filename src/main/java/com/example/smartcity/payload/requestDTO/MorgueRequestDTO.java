package com.example.smartcity.payload.requestDTO;

import lombok.Data;

import java.util.Date;

@Data
public class MorgueRequestDTO {
    private long corpseCardNumber;

    private Date deathDate;

    private String cause;
}
