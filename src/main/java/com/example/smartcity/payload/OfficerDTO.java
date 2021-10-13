package com.example.smartcity.payload;

import com.example.smartcity.entity.enums.OfficerRank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficerDTO {

    private long cardNumber;

    private OfficerRank rank;

    private UUID stationId;
}
