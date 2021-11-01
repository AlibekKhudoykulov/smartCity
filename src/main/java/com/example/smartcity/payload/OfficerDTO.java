package com.example.smartcity.payload;

import com.example.smartcity.entity.enums.OfficerRank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficerDTO {

    @NotNull(message = "Card number must not be empty")
    private long cardNumber;

    private OfficerRank rank;

    private UUID stationId;
}
