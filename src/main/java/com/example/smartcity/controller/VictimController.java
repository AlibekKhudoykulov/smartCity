package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.VictimResponseDTO;
import com.example.smartcity.service.impl.VictimServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/victim")
@RequiredArgsConstructor
public class VictimController {

    private final VictimServiceImpl victimService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page){
        List<VictimResponseDTO> allVictims = victimService.getAllVictims(page);
        return new ResponseEntity<List<VictimResponseDTO>>(allVictims,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        VictimResponseDTO victimById = victimService.getVictimById(id);
        return new ResponseEntity<VictimResponseDTO>(victimById, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody VictimDTO victimDTO){
        return victimService.addVictim(victimDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody VictimDTO victimDTO){
        return victimService.editVictim(id,victimDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return victimService.deleteVictim(id);
    }
}
