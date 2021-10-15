package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.Crime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VictimResponseDTO {
    private UUID id;

    private Long cardNumber;

    private String name;

    private String surname;

    private Date birthDate;

    private Date deathDate;

    private String remark;

    private UUID photoId;

    private boolean isDead;

    private List<CrimeResponseDTO> crimes;
}
