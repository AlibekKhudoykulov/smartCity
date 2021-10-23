package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PrisonerResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PrisonerService {

    PrisonerResponseDTO getPrisonerById(UUID id);

    CustomPage<PrisonerResponseDTO> getAllArrestedPeople(Integer page);

    ApiResponse addPrisoner(PrisonerDTO prisonerDTO);

    ApiResponse editPrisoner(UUID id,PrisonerDTO prisonerDTO);

    ApiResponse deletePrisoner(UUID id);
}
