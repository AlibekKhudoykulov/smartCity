package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.Victim;
import com.example.smartcity.Repository.VictimRepository;
import com.example.smartcity.Service.VictimService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VictimServiceImpl implements VictimService {

    @Autowired
    private VictimRepository victimRepository;

    @Override
    public ApiResponse getAllVictims() {
        List<Victim> all = victimRepository.findAll();
        return new ApiResponse("Success",true,all);
    }

    @Override
    public ApiResponse getVictimById(UUID id) {
        Victim victim = victimRepository.findById(id)
                .orElseThrow(() -> new RestException("Victim not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success",true, victim);
    }

    @Override
    public ApiResponse addVictim(VictimDTO victimDTO) {
        return null;
    }

    @Override
    public ApiResponse editVictim(UUID id,VictimDTO victimDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteVictim(UUID id) {
        try {
            victimRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Victim Not found", HttpStatus.NOT_FOUND);
        }
    }
}
