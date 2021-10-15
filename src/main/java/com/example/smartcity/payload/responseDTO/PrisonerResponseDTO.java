package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.Crime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PrisonerResponseDTO {
    private UUID id;

    private Long cardNumber;

    private String firstName;

    private String surname;

    private Date birthDate;

    private String prisonDuration;

    private Date startingDate;

    private Date endingDate;

    private boolean inPrison;

    private UUID photoId;

    private List<CrimeResponseDTO> crimes;
}
