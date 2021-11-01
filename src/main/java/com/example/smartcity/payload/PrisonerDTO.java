package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrisonerDTO {

    @NotNull(message = "Card number must not be empty")
    private long cardNumber;

    private String prisonDuration;

    private Date startingDate;

    private Date endingDate;

    private boolean inPrison;

    private List<UUID> crimes;

}
