package com.example.smartcity.Service;

import com.example.smartcity.payload.ApiResponse;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface CrimeService {

    ApiResponse getAllCrimes();

    ApiResponse getCrimeById(UUID id);

    ApiResponse addCrime();

    ApiResponse editCrime();

    ApiResponse deleteCrime(UUID id);
}
