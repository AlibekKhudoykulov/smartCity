package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import com.example.smartcity.payload.responseDTO.OfficerResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface OfficerService {

    List<OfficerResponseDTO> getAllOfficers(Integer page);

    OfficerResponseDTO getOfficerById(UUID id);

    OfficerResponseDTO getOfficerByCardNumber(long cardNumber);

    ApiResponse addOfficer(OfficerDTO officerDTO);

    ApiResponse editOfficer(UUID id,OfficerDTO officerDTO);

    ApiResponse deleteOfficer(UUID id);

}
