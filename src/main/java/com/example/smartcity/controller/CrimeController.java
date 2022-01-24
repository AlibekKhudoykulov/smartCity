package com.example.smartcity.controller;

import com.example.smartcity.constants.PathConstant;
import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.service.impl.CrimeServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.CrimeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(PathConstant.CRIME_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin
public class CrimeController {

    private final CrimeServiceImpl crimeService;

    @GetMapping
    public ResponseEntity<CustomPage<CrimeResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<CrimeResponseDTO> allCrimes = crimeService.getAllCrimes(page);
        return new ResponseEntity<CustomPage<CrimeResponseDTO>>(allCrimes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrimeResponseDTO> getCrimeById(@PathVariable UUID id){
        CrimeResponseDTO crimeById = crimeService.getCrimeById(id);
        return new ResponseEntity<CrimeResponseDTO>(crimeById,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addCrime(@RequestBody @Valid CrimeDTO crimeDTO){
        ApiResponse apiResponse = crimeService.addCrime(crimeDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCrime(@PathVariable UUID id,@RequestBody @Valid CrimeDTO crimeDTO){
        ApiResponse apiResponse = crimeService.editCrime(id, crimeDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCrime(@PathVariable UUID id){
        ApiResponse apiResponse = crimeService.deleteCrime(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
