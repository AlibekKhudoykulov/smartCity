package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PoliceStationDTO {

    private String name;

    private String address;

    private String phoneNumber;

    private String remark;
}
