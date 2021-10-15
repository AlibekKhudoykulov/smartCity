package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PoliceStationService {

    List<PoliceStationResponseDTO> getAllStations(Integer page);

    PoliceStationResponseDTO getStationById(UUID id);

    ResponseEntity<?> addStation(PoliceStationDTO policeStationDTO);

    ResponseEntity<?> editStation(UUID id, PoliceStationDTO policeStationDTO);

    ResponseEntity<?> deleteStation(UUID id);

}
