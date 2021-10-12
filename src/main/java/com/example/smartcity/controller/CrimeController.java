package com.example.smartcity.controller;

import com.example.smartcity.service.impl.CrimeServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/crime")
@RequiredArgsConstructor
public class CrimeController {

    private final CrimeServiceImpl crimeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return crimeService.getAllCrimes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrimeById(@PathVariable UUID id){
        return crimeService.getCrimeById(id);
    }

    @PostMapping
    public ResponseEntity<?> addCrime(@RequestBody CrimeDTO crimeDTO){
        return crimeService.addCrime(crimeDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editCrime(@PathVariable UUID id,@RequestBody CrimeDTO crimeDTO){
        return crimeService.editCrime(id,crimeDTO);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrime(@PathVariable UUID id){
        return crimeService.deleteCrime(id);
    }
}
