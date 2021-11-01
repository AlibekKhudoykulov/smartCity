package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@SuperBuilder
public class PoliceStationDTO {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Name must not be empty")
    private String address;

    private String phoneNumber;

    private String remark;
}
