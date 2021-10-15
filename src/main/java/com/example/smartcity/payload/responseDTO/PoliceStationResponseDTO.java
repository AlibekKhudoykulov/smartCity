package com.example.smartcity.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PoliceStationResponseDTO {
    private UUID id;

    private String name;

    private String address;

    private String phoneNumber;

    private String remark;
}
