package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OfficerResponseDTO {

    private UUID uuid;

    private long cardNumber;

    private String firstName;

    private String lastName;

    private OfficerRank rank;

    private PoliceStationResponseDTO policeStation;

    private Date birthDate;

    private long certificate;

    private UUID photoId;

}
