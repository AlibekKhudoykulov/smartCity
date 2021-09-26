package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.Witness;
import com.example.smartcity.Repository.WitnessRepository;
import com.example.smartcity.Service.WitnessService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WitnessServiceImpl implements WitnessService {

    @Autowired
    private WitnessRepository witnessRepository;

    @Override
    public ApiResponse getAllWitnesses() {
        List<Witness> witnesses = witnessRepository.findAll();
        return new ApiResponse("Success",true,witnesses);
    }

    @Override
    public ApiResponse getWitnessById(UUID id) {
        Witness witness = witnessRepository.findById(id)
                .orElseThrow(() -> new RestException("Witness not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success",true,witness);
    }

    @Override
    public ApiResponse addWitness(WitnessDTO witnessDTO) {
        return null;
    }

    @Override
    public ApiResponse editWitness(UUID id, WitnessDTO witnessDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteWitness(UUID id) {
        try {
            witnessRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Witness Not found", HttpStatus.NOT_FOUND);
        }
    }
}
