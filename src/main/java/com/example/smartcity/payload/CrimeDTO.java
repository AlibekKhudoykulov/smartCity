package com.example.smartcity.payload;

import com.example.smartcity.entity.enums.CrimeReportStatus;
import com.example.smartcity.entity.enums.CrimeStatus;
import com.example.smartcity.entity.enums.CrimeType;
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

    private String crimeDescription;

    private List<UUID> officers;

    private CrimeType crimeType;

    private UUID policeStationId;

    private CrimeReportStatus crimeReportStatus;

    private CrimeStatus crimeStatus;

}
