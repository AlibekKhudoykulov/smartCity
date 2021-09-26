package com.example.smartcity.Service;

import com.example.smartcity.Entity.PoliceStation;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;

import java.util.UUID;

/**
 * @author Alibek Khudoykulov
 */

public interface PoliceStationService {

    ApiResponse getAllStations();

    ApiResponse getStationById(UUID id);

    ApiResponse addStation(PoliceStationDTO policeStationDTO);

    ApiResponse editStation(UUID id, PoliceStationDTO policeStationDTO);

    ApiResponse deleteStation(UUID id);

}
