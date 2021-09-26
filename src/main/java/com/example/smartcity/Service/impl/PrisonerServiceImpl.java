package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.Prisoner;
import com.example.smartcity.Repository.PrisonerRepository;
import com.example.smartcity.Service.PrisonerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrisonerServiceImpl implements PrisonerService {

    @Autowired
    private PrisonerRepository prisonerRepository;

    @Override
    public ApiResponse getAllArrestedPeople() {
        List<Prisoner> prisoners = prisonerRepository.findAll();
        return new ApiResponse("Success", true, prisoners);
    }

    @Override
    public ApiResponse getPrisonerById(UUID id) {
        Prisoner prisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RestException("Prisoner not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success", true, prisoner);
    }

    @Override
    public ApiResponse addPrisoner(PrisonerDTO prisonerDTO) {
        return null;
    }

    @Override
    public ApiResponse editPrisoner(UUID id, PrisonerDTO prisonerDTO) {
        return null;
    }

    @Override
    public ApiResponse deletePrisoner(UUID id) {
        try {
            prisonerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Prisoner Not found", HttpStatus.NOT_FOUND);
        }
    }
}
