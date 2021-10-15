package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.Officer;
import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CrimeResponseDTO {
    private UUID uuid;

    private String name;

    private String address;

    private LocalDateTime crimeTime;

    private String crimeDescription;

    private List<OfficerResponseDTO> officers;

    private PoliceStationDTO policeStation;

    private CrimeReportStatus crimeReportStatus;

    private CrimeStatus crimeStatus;

    private CrimeType crimeType;
}
