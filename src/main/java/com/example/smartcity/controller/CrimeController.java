package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.service.impl.CrimeServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crime")
@RequiredArgsConstructor
public class CrimeController {

    private final CrimeServiceImpl crimeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page){
        List<CrimeResponseDTO> allCrimes = crimeService.getAllCrimes(page);
        return new ResponseEntity<List<CrimeResponseDTO>>(allCrimes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCrimeById(@PathVariable UUID id){
        CrimeResponseDTO crimeById = crimeService.getCrimeById(id);
        return new ResponseEntity<CrimeResponseDTO>(crimeById,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCrime(@RequestBody CrimeDTO crimeDTO){
        ApiResponse apiResponse = crimeService.addCrime(crimeDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editCrime(@PathVariable UUID id,@RequestBody CrimeDTO crimeDTO){
        ApiResponse apiResponse = crimeService.editCrime(id, crimeDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCrime(@PathVariable UUID id){
        ApiResponse apiResponse = crimeService.deleteCrime(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
