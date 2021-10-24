package com.example.smartcity.service;

import com.example.smartcity.payload.requestDTO.CityManagementRequestDTO;
import com.example.smartcity.payload.requestDTO.MorgueRequestDTO;
import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.MorgueResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ReceiveRequestAPIService {
    ResponseEntity<?> getCheckingCertificate(CityManagementRequestDTO cityManagementRequestDTO);

    ResponseEntity<?> askPoliceOfficerForMorgue(MorgueRequestDTO morgueRequestDTO);

    ResponseEntity<?> getRequestToBlockAccountFromMorgue(MorgueRequestDTO morgueRequestDTO);

    CustomPage<MorgueResponseDTO> getAllRequests(Integer page);

}
