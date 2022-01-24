package com.example.smartcity.controller;

import com.example.smartcity.constants.PathConstant;
import com.example.smartcity.payload.responseDTO.CustomPage;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PathConstant.VICTIM_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin
public class VictimController {

    private final VictimServiceImpl victimService;

    @GetMapping
    public ResponseEntity<CustomPage<VictimResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<VictimResponseDTO> allVictims = victimService.getAllVictims(page);
        return new ResponseEntity<CustomPage<VictimResponseDTO>>(allVictims,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VictimResponseDTO> getById(@PathVariable UUID id){
        VictimResponseDTO victimById = victimService.getVictimById(id);
        return new ResponseEntity<VictimResponseDTO>(victimById, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid VictimDTO victimDTO){
        ApiResponse apiResponse = victimService.addVictim(victimDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id,@RequestBody @Valid VictimDTO victimDTO){
        ApiResponse apiResponse = victimService.editVictim(id, victimDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        ApiResponse apiResponse = victimService.deleteVictim(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
