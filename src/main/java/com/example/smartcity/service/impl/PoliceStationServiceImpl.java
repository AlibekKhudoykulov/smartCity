package com.example.smartcity.service.impl;

import com.example.smartcity.entity.PoliceStation;
import com.example.smartcity.repository.PoliceStationRepository;
import com.example.smartcity.service.PoliceStationService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Optional<PoliceStation> byName = policeStationRepository.findByName(policeStationDTO.getName());
        if (byName.isPresent()) throw new RestException("Already exists this Police Station",HttpStatus.CONFLICT);

        PoliceStation policeStation=new PoliceStation(
                policeStationDTO.getName(),
                policeStationDTO.getAddress(),
                policeStationDTO.getPhoneNumber(),
                policeStationDTO.getRemark()
        );

        policeStationRepository.save(policeStation);

        return new ApiResponse("Police Station Saved Successfully",true);
    }

    @Override
    public ApiResponse editStation(UUID id, PoliceStationDTO policeStationDTO) {
        PoliceStation station = policeStationRepository.findById(id)
                .orElseThrow(() -> new RestException("Police Station Not found",HttpStatus.NOT_FOUND));
        station.setName(policeStationDTO.getName());
        station.setAddress(policeStationDTO.getAddress());
        station.setPhoneNumber(policeStationDTO.getPhoneNumber());
        station.setRemark(policeStationDTO.getRemark());

        policeStationRepository.save(station);

        return new ApiResponse("Edited Successfully",true);
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
