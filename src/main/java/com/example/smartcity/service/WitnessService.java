package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface WitnessService {

    ApiResponse getWitnessById(UUID id);

    ApiResponse getAllWitnesses();

    ApiResponse addWitness(WitnessDTO witnessDTO);

    ApiResponse editWitness(UUID id,WitnessDTO witnessDTO);

    ApiResponse deleteWitness(UUID id);
}
