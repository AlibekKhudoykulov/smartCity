package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface CrimeService {

    ResponseEntity<?> getAllCrimes(Integer page);

    ResponseEntity<?> getCrimeById(UUID id);

    ResponseEntity<?> addCrime(CrimeDTO crimeDTO);

    ResponseEntity<?> editCrime(UUID id,CrimeDTO crimeDTO);

    ResponseEntity<?> deleteCrime(UUID id);
}
