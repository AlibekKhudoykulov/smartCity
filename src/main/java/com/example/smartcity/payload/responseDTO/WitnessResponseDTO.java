package com.example.smartcity.payload.responseDTO;

import com.example.smartcity.entity.Crime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WitnessResponseDTO {

    private UUID id;

    private Long cardNumber;

    private String firstName;

    private String surname;

    private Date birthDate;

    private String phoneNumber;

    private String remark;

    private UUID photoId;

    private List<CrimeResponseDTO> crime;
}
