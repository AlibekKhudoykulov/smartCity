package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PrisonerService {

    ApiResponse getPrisonerById(UUID id);

    ApiResponse getAllArrestedPeople();

    ApiResponse addPrisoner(PrisonerDTO prisonerDTO);

    ApiResponse editPrisoner(UUID id,PrisonerDTO prisonerDTO);

    ApiResponse deletePrisoner(UUID id);
}
