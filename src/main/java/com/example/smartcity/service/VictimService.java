package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface VictimService {

    ResponseEntity<?> getAllVictims();

    ResponseEntity<?> getVictimById(UUID id);

    ResponseEntity<?> addVictim(VictimDTO victimDTO);

    ResponseEntity<?> editVictim(UUID id,VictimDTO victimDTO);

    ResponseEntity<?> deleteVictim(UUID id);
}
