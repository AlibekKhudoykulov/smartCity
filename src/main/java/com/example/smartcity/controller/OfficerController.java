package com.example.smartcity.controller;

import com.example.smartcity.service.impl.OfficerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/officer")
@RequiredArgsConstructor
public class OfficerController {

    private final OfficerServiceImpl officerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        return officerService.getAllOfficers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfficerById(@PathVariable UUID id){
        return officerService.getOfficerById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody OfficerDTO officerDTO){
        return officerService.addOfficer(officerDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody OfficerDTO officerDTO){
        return officerService.editOfficer(id,officerDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return officerService.deleteOfficer(id);
    }
}
