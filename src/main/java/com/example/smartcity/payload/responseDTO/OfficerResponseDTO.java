package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.entity.enums.OfficerRank;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@SuperBuilder
public class OfficerResponseDTO {

    private UUID uuid;

    private long cardNumber;

    private String firstName;

    private String lastName;

    private OfficerRank rank;

    private PoliceStationDTO policeStation;

    private Date birthDate;

    private long certificate;

    private UUID photoId;

    public OfficerResponseDTO(UUID uuid, long cardNumber, String firstName, String lastName, OfficerRank rank, PoliceStationDTO policeStation) {
        this.uuid = uuid;
        this.cardNumber = cardNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.policeStation = policeStation;
    }
}
