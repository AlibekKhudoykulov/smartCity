package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.Officer;
import com.example.smartcity.Repository.OfficerRepository;
import com.example.smartcity.Service.OfficerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OfficerServiceImpl implements OfficerService {

    @Autowired
    private OfficerRepository officerRepository;

    @Override
    public ApiResponse getAllOfficers() {
        List<Officer> officerList = officerRepository.findAll();
        return new ApiResponse("Officer list", true, officerList);
    }

    @Override
    public ApiResponse getOfficerById(UUID id) {
        Officer officer = officerRepository.findById(id)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("success", true, officer);
    }

    @Override
    public ApiResponse getOfficerByCardNumber(long cardNumber) {
        Officer officer = officerRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RestException("Officer not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success", true, officer);
    }

    @Override
    public ApiResponse addOfficer(OfficerDTO officerDTO) {
        Optional<Officer> byCardNumber = officerRepository.findByCardNumber(officerDTO.getCardNumber());
        if (byCardNumber.isPresent()) return new ApiResponse("Already added",false);

        return null;
    }

    @Override
    public ApiResponse editOfficer(UUID id, OfficerDTO officerDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteOfficer(UUID id) {
        try {
            officerRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Officer Not found", HttpStatus.NOT_FOUND);
        }
    }
}
