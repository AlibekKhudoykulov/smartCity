package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.Officer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.OneToOne;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MorgueResponseDTO {
    private UUID uuid;

    private long corpseCardNumber;

    private long OfficerCardNumber;

    private Date deathDate;

    private String causes;

    private boolean endExamination;

    private OfficerResponseDTO officer;
}
