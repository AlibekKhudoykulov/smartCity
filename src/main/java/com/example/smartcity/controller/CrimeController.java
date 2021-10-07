package com.example.smartcity.controller;

import com.example.smartcity.service.impl.CrimeServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/crime")
public class CrimeController {

    @Autowired
    private CrimeServiceImpl crimeService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse allCrimes = crimeService.getAllCrimes();
        return ResponseEntity.status(allCrimes.isSuccess()?200:404).body(allCrimes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrimeById(@PathVariable UUID id){
        ApiResponse crimeById = crimeService.getCrimeById(id);
        return ResponseEntity.status(crimeById.isSuccess()?200:404).body(crimeById);
    }

    @PostMapping
    public ResponseEntity<?> addCrime(@RequestBody CrimeDTO crimeDTO){
        ApiResponse crimeById = crimeService.addCrime(crimeDTO);
        return ResponseEntity.status(crimeById.isSuccess()?201:400).body(crimeById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCrime(@PathVariable UUID id,@RequestBody CrimeDTO crimeDTO){
        ApiResponse crime = crimeService.editCrime(id,crimeDTO);
        return ResponseEntity.status(crime.isSuccess()?201:400).body(crime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrime(@PathVariable UUID id){
        ApiResponse crimeById = crimeService.deleteCrime(id);
        return ResponseEntity.status(crimeById.isSuccess()?200:404).body(crimeById);
    }
}
