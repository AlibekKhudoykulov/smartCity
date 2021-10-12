package com.example.smartcity.controller;

import com.example.smartcity.service.impl.PrisonerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.PrisonerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/prisoner")
@RequiredArgsConstructor
public class PrisonerController {

    private final PrisonerServiceImpl prisonerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return prisonerService.getAllArrestedPeople();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        return prisonerService.getPrisonerById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PrisonerDTO prisonerDTO){
        return prisonerService.addPrisoner(prisonerDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody PrisonerDTO prisonerDTO){
        return prisonerService.editPrisoner(id,prisonerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return prisonerService.deletePrisoner(id);
    }
}
