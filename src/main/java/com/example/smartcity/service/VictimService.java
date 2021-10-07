package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface VictimService {

    ApiResponse getAllVictims();

    ApiResponse getVictimById(UUID id);

    ApiResponse addVictim(VictimDTO victimDTO);

    ApiResponse editVictim(UUID id,VictimDTO victimDTO);

    ApiResponse deleteVictim(UUID id);
}
