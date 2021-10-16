package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import com.example.smartcity.payload.responseDTO.WitnessResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface WitnessService {

    WitnessResponseDTO getWitnessById(UUID id);

    List<WitnessResponseDTO> getAllWitnesses(Integer page);

    ApiResponse addWitness(WitnessDTO witnessDTO);

    ApiResponse editWitness(UUID id,WitnessDTO witnessDTO);

    ApiResponse deleteWitness(UUID id);
}
