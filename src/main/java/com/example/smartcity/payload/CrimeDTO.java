package com.example.smartcity.payload;

import com.example.smartcity.Entity.Enums.CrimeReportStatus;
import com.example.smartcity.Entity.Enums.CrimeStatus;
import com.example.smartcity.Entity.Enums.CrimeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrimeDTO {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Address must not be empty")
    private String address;

    @NotBlank(message = "Crime Description must not be empty")
    private String crimeDescription;

    private List<Long> witnessCardNumbers;

    private List<Long> prisonerCardNumbers;

    private List<Long> victimCardNumbers;

    private List<Long> officerCardNumbers;

    @NotBlank(message = "CrimeType must not be empty")
    private CrimeType crimeType;

    private UUID policeStationId;

    private CrimeReportStatus crimeReportStatus;

    private CrimeStatus crimeStatus;

    public CrimeDTO(String name, String address, String crimeDescription, CrimeType crimeType) {
        this.name = name;
        this.address = address;
        this.crimeDescription = crimeDescription;
        this.crimeType = crimeType;
    }
}
