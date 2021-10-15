package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class PoliceStationDTO {

    private String name;

    private String address;

    private String phoneNumber;

    private String remark;
}
