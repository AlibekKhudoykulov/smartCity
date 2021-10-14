package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface WitnessService {

    ResponseEntity<?> getWitnessById(UUID id);

    ResponseEntity<?> getAllWitnesses(Integer page);

    ResponseEntity<?> addWitness(WitnessDTO witnessDTO);

    ResponseEntity<?> editWitness(UUID id,WitnessDTO witnessDTO);

    ResponseEntity<?> deleteWitness(UUID id);
}
