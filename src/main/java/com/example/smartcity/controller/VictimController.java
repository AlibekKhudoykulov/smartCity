package com.example.smartcity.controller;

import com.example.smartcity.service.impl.VictimServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.VictimDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/victim")
public class VictimController {

    @Autowired
    private VictimServiceImpl victimService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse victims = victimService.getAllVictims();
        return ResponseEntity.status(victims.isSuccess()?200:404).body(victims);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        ApiResponse victim = victimService.getVictimById(id);
        return ResponseEntity.status(victim.isSuccess()?200:404).body(victim);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody VictimDTO victimDTO){
        ApiResponse victim = victimService.addVictim(victimDTO);
        return ResponseEntity.status(victim.isSuccess()?201:400).body(victim);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody VictimDTO victimDTO){
        ApiResponse victim = victimService.editVictim(id,victimDTO);
        return ResponseEntity.status(victim.isSuccess()?201:400).body(victim);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse victim = victimService.deleteVictim(id);
        return ResponseEntity.status(victim.isSuccess()?200:404).body(victim);
    }
}
