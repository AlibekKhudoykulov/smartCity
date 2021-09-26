package com.example.smartcity.Service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface CrimeService {

    ApiResponse getAllCrimes();

    ApiResponse getCrimeById(UUID id);

    ApiResponse addCrime(CrimeDTO crimeDTO);

    ApiResponse editCrime(UUID id,CrimeDTO crimeDTO);

    ApiResponse deleteCrime(UUID id);
}
