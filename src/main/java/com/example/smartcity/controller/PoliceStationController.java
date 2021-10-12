package com.example.smartcity.controller;

import com.example.smartcity.service.impl.PoliceStationServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PoliceStationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/police")
@RequiredArgsConstructor
public class PoliceStationController {

    private final PoliceStationServiceImpl policeStationService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return policeStationService.getAllStations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        return policeStationService.getStationById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PoliceStationDTO policeStationDTO){
        return policeStationService.addStation(policeStationDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody PoliceStationDTO policeStationDTO){
        return policeStationService.editStation(id,policeStationDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return policeStationService.deleteStation(id);
    }
}
