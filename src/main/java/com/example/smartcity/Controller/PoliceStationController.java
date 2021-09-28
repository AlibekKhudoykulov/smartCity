package com.example.smartcity.Controller;

import com.example.smartcity.Service.PoliceStationService;
import com.example.smartcity.Service.impl.CrimeServiceImpl;
import com.example.smartcity.Service.impl.PoliceStationServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import com.example.smartcity.payload.PoliceStationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/police")
public class PoliceStationController {

    @Autowired
    private PoliceStationServiceImpl policeStationService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse policeStation = policeStationService.getAllStations();
        return ResponseEntity.status(policeStation.isSuccess()?200:404).body(policeStation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        ApiResponse policeStation = policeStationService.getStationById(id);
        return ResponseEntity.status(policeStation.isSuccess()?200:404).body(policeStation);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody PoliceStationDTO policeStationDTO){
        ApiResponse policeStation = policeStationService.addStation(policeStationDTO);
        return ResponseEntity.status(policeStation.isSuccess()?201:400).body(policeStation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody PoliceStationDTO policeStationDTO){
        ApiResponse policeStation = policeStationService.editStation(id,policeStationDTO);
        return ResponseEntity.status(policeStation.isSuccess()?201:400).body(policeStation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse policeStation = policeStationService.deleteStation(id);
        return ResponseEntity.status(policeStation.isSuccess()?200:404).body(policeStation);
    }
}