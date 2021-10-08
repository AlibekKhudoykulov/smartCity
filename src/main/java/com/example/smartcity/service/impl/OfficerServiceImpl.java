package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Officer;
import com.example.smartcity.repository.OfficerRepository;
import com.example.smartcity.service.OfficerService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfficerServiceImpl implements OfficerService {

    private final OfficerRepository officerRepository;

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
