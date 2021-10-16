package com.example.smartcity.controller;

import com.example.smartcity.payload.responseDTO.OfficerResponseDTO;
import com.example.smartcity.service.impl.OfficerServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.OfficerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/officer")
@RequiredArgsConstructor
public class OfficerController {

    private final OfficerServiceImpl officerService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam Integer page){
        List<OfficerResponseDTO> allOfficers = officerService.getAllOfficers(page);
        return new ResponseEntity<List<OfficerResponseDTO>>(allOfficers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOfficerById(@PathVariable UUID id){
        OfficerResponseDTO officerById = officerService.getOfficerById(id);
        return new ResponseEntity<OfficerResponseDTO>(officerById,HttpStatus.OK);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<?> getOfficerById(@PathVariable Long cardNumber){
        OfficerResponseDTO officerByCardNumber = officerService.getOfficerByCardNumber(cardNumber);
        return new ResponseEntity<OfficerResponseDTO>(officerByCardNumber,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody OfficerDTO officerDTO){
        ApiResponse apiResponse = officerService.addOfficer(officerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody OfficerDTO officerDTO){
        ApiResponse apiResponse = officerService.editOfficer(id, officerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse apiResponse = officerService.deleteOfficer(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
