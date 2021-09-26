package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.PoliceStation;
import com.example.smartcity.Repository.PoliceStationRepository;
import com.example.smartcity.Service.PoliceStationService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class PoliceStationServiceImpl implements PoliceStationService {

    @Autowired
    private PoliceStationRepository policeStationRepository;

    @Override
    public ApiResponse getAllStations() {
        List<PoliceStation> policeStationList = policeStationRepository.findAll();
        return new ApiResponse("Success",true,policeStationList);
    }

    @Override
    public ApiResponse getStationById(UUID id) {
        PoliceStation byId = policeStationRepository.findById(id)
                .orElseThrow(()->new RestException("Police Station Not found", HttpStatus.NOT_FOUND));

        return new ApiResponse("Success",true,byId);
    }

    @Override
    public ApiResponse addStation(PoliceStationDTO policeStationDTO) {
        return null;
    }

    @Override
    public ApiResponse editStation(UUID id, PoliceStationDTO policeStationDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteStation(UUID id) {
        try {
            policeStationRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("Police Station Not found", HttpStatus.NOT_FOUND);
        }
    }
}
