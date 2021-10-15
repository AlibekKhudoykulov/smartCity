package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.WitnessResponseDTO;
import com.example.smartcity.service.impl.WitnessServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/witness")
@RequiredArgsConstructor
public class WitnessController {

    private final WitnessServiceImpl witnessService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page){
        List<WitnessResponseDTO> allWitnesses = witnessService.getAllWitnesses(page);
        return new ResponseEntity<List<WitnessResponseDTO>>(allWitnesses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        WitnessResponseDTO witnessById = witnessService.getWitnessById(id);
        return new ResponseEntity<WitnessResponseDTO>(witnessById, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody WitnessDTO witnessDTO){
        return witnessService.addWitness(witnessDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody WitnessDTO witnessDTO){
        return witnessService.editWitness(id,witnessDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return witnessService.deleteWitness(id);
    }
}
