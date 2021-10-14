package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PrisonerService {

    ResponseEntity<?> getPrisonerById(UUID id);

    ResponseEntity<?> getAllArrestedPeople(Integer page);

    ResponseEntity<?> addPrisoner(PrisonerDTO prisonerDTO);

    ResponseEntity<?> editPrisoner(UUID id,PrisonerDTO prisonerDTO);

    ResponseEntity<?> deletePrisoner(UUID id);
}
