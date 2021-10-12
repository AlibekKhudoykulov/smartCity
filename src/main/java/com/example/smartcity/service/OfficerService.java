package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */
public interface OfficerService {

    ResponseEntity<?> getAllOfficers();

    ResponseEntity<?> getOfficerById(UUID id);

    ResponseEntity<?> getOfficerByCardNumber(long cardNumber);

    ResponseEntity<?> addOfficer(OfficerDTO officerDTO);

    ResponseEntity<?> editOfficer(UUID id,OfficerDTO officerDTO);

    ResponseEntity<?> deleteOfficer(UUID id);

}
