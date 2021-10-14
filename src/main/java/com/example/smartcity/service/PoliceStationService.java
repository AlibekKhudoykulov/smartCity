package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PoliceStationService {

    ResponseEntity<?> getAllStations(Integer page);

    ResponseEntity<?> getStationById(UUID id);

    ResponseEntity<?> addStation(PoliceStationDTO policeStationDTO);

    ResponseEntity<?> editStation(UUID id, PoliceStationDTO policeStationDTO);

    ResponseEntity<?> deleteStation(UUID id);

}
