package com.example.smartcity.controller;

import com.example.smartcity.constants.PathConstant;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.WitnessResponseDTO;
import com.example.smartcity.service.impl.WitnessServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.WitnessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PathConstant.WITNESS_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin
public class WitnessController {

    private final WitnessServiceImpl witnessService;

    @GetMapping
    public ResponseEntity<CustomPage<WitnessResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<WitnessResponseDTO> allWitnesses = witnessService.getAllWitnesses(page);
        return new ResponseEntity<CustomPage<WitnessResponseDTO>>(allWitnesses,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WitnessResponseDTO> getById(@PathVariable UUID id){
        WitnessResponseDTO witnessById = witnessService.getWitnessById(id);
        return new ResponseEntity<WitnessResponseDTO>(witnessById, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid WitnessDTO witnessDTO){
        ApiResponse apiResponse = witnessService.addWitness(witnessDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id,@RequestBody @Valid WitnessDTO witnessDTO){
        ApiResponse apiResponse = witnessService.editWitness(id, witnessDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        ApiResponse apiResponse = witnessService.deleteWitness(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
