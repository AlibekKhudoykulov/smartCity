package com.example.smartcity.Service;

import com.example.smartcity.payload.ApiResponse;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface VictimService {

    ApiResponse getAllVictims();

    ApiResponse getVictimById(UUID id);

    ApiResponse addVictim();

    ApiResponse editVictim();

    ApiResponse deleteVictim(UUID id);
}
