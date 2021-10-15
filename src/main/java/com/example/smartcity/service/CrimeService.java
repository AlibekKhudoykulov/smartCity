package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface CrimeService {

    List<CrimeResponseDTO> getAllCrimes(Integer page);

    CrimeResponseDTO getCrimeById(UUID id);

    ResponseEntity<?> addCrime(CrimeDTO crimeDTO);

    ResponseEntity<?> editCrime(UUID id,CrimeDTO crimeDTO);

    ResponseEntity<?> deleteCrime(UUID id);
}
