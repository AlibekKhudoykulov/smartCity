package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface OfficerService {

    ApiResponse getAllOfficers();

    ApiResponse getOfficerById(UUID id);

    ApiResponse getOfficerByCardNumber(long cardNumber);

    ApiResponse addOfficer(OfficerDTO officerDTO);

    ApiResponse editOfficer(UUID id,OfficerDTO officerDTO);

    ApiResponse deleteOfficer(UUID id);

}
