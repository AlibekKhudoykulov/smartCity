package com.example.smartcity.service;

import com.example.smartcity.payload.requestDTO.CityManagementRequestDTO;
import com.example.smartcity.payload.requestDTO.MorgueRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ReceiveRequestAPIService {
    ResponseEntity<?> getCheckingCertificate(CityManagementRequestDTO cityManagementRequestDTO);

    ResponseEntity<?> askPoliceOfficerForMorgue(MorgueRequestDTO morgueRequestDTO);

    ResponseEntity<?> getRequestToBlockAccountFromMorgue(MorgueRequestDTO morgueRequestDTO);
}
