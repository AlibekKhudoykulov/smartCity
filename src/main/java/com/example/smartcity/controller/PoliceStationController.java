package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PoliceStationResponseDTO;
import com.example.smartcity.service.impl.PoliceStationServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/police")
@RequiredArgsConstructor
@CrossOrigin
public class PoliceStationController {

    private final PoliceStationServiceImpl policeStationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<CustomPage<PoliceStationResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<PoliceStationResponseDTO> allStations = policeStationService.getAllStations(page);
        return new ResponseEntity<CustomPage<PoliceStationResponseDTO>>(allStations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PoliceStationResponseDTO> getById(@PathVariable UUID id){
        PoliceStationResponseDTO stationById = policeStationService.getStationById(id);
        return new ResponseEntity<PoliceStationResponseDTO>(stationById,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody PoliceStationDTO policeStationDTO){
        ApiResponse apiResponse = policeStationService.addStation(policeStationDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id,@RequestBody PoliceStationDTO policeStationDTO){
        ApiResponse apiResponse = policeStationService.editStation(id, policeStationDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        ApiResponse apiResponse = policeStationService.deleteStation(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

    }
}
