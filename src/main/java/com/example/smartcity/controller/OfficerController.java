package com.example.smartcity.controller;

import com.example.smartcity.constants.PathConstant;
import com.example.smartcity.payload.responseDTO.CustomPage;
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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(PathConstant.OFFICER_CONTROLLER)
@RequiredArgsConstructor
@CrossOrigin
public class OfficerController {

    private final OfficerServiceImpl officerService;

    @GetMapping
    public ResponseEntity<CustomPage<OfficerResponseDTO>> getAll(@RequestParam Integer page){
        CustomPage<OfficerResponseDTO> allOfficers = officerService.getAllOfficers(page);
        return new ResponseEntity<CustomPage<OfficerResponseDTO>>(allOfficers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfficerResponseDTO> getOfficerById(@PathVariable UUID id){
        OfficerResponseDTO officerById = officerService.getOfficerById(id);
        return new ResponseEntity<OfficerResponseDTO>(officerById,HttpStatus.OK);
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<OfficerResponseDTO> getOfficerById(@PathVariable Long cardNumber){
        OfficerResponseDTO officerByCardNumber = officerService.getOfficerByCardNumber(cardNumber);
        return new ResponseEntity<OfficerResponseDTO>(officerByCardNumber,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody @Valid OfficerDTO officerDTO){
        ApiResponse apiResponse = officerService.addOfficer(officerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id,@RequestBody @Valid OfficerDTO officerDTO){
        ApiResponse apiResponse = officerService.editOfficer(id, officerDTO);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id){
        ApiResponse apiResponse = officerService.deleteOfficer(id);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
