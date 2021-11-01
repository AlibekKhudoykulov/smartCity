package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.PrisonerResponseDTO;
import com.example.smartcity.service.impl.PrisonerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prisoner")
@RequiredArgsConstructor
@CrossOrigin
public class PrisonerController {

    private final PrisonerServiceImpl prisonerService;

    @GetMapping
    public ResponseEntity<CustomPage<PrisonerResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<PrisonerResponseDTO> allArrestedPeople = prisonerService.getAllArrestedPeople(page);
        return new ResponseEntity<CustomPage<PrisonerResponseDTO>>(allArrestedPeople, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrisonerResponseDTO> getById(@PathVariable UUID id){
        PrisonerResponseDTO prisonerById = prisonerService.getPrisonerById(id);
        return new ResponseEntity<PrisonerResponseDTO>(prisonerById,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid PrisonerDTO prisonerDTO){
        ApiResponse apiResponse = prisonerService.addPrisoner(prisonerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id,@RequestBody @Valid PrisonerDTO prisonerDTO){
        ApiResponse apiResponse = prisonerService.editPrisoner(id, prisonerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        ApiResponse apiResponse = prisonerService.deletePrisoner(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);

    }
}
