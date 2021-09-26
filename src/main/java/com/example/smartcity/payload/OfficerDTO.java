package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OfficerDTO {

    private String rank;

    private long cardNumber;

    private UUID stationId;
}
