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

    ResponseEntity<?> addWitness(WitnessDTO witnessDTO);

    ResponseEntity<?> editWitness(UUID id,WitnessDTO witnessDTO);

    ResponseEntity<?> deleteWitness(UUID id);
}
