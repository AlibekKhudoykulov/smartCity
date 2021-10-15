package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import com.example.smartcity.payload.responseDTO.PrisonerResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PrisonerService {

    PrisonerResponseDTO getPrisonerById(UUID id);

    List<PrisonerResponseDTO> getAllArrestedPeople(Integer page);

    ResponseEntity<?> addPrisoner(PrisonerDTO prisonerDTO);

    ResponseEntity<?> editPrisoner(UUID id,PrisonerDTO prisonerDTO);

    ResponseEntity<?> deletePrisoner(UUID id);
}
