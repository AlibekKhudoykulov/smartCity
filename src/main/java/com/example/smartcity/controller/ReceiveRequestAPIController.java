package com.example.smartcity.controller;

import com.example.smartcity.payload.requestDTO.CityManagementRequestDTO;
import com.example.smartcity.payload.requestDTO.MorgueRequestDTO;
import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import com.example.smartcity.payload.responseDTO.MorgueResponseDTO;
import com.example.smartcity.security.hmac.AuthorizeRequest;
import com.example.smartcity.service.impl.ReceiveRequestAPIServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
@CrossOrigin
public class ReceiveRequestAPIController {

    private final ReceiveRequestAPIServiceImpl receiveRequestAPIService;

    @PostMapping("/certificate")
    @AuthorizeRequest
    public ResponseEntity<?> checkingCertificateRequest(@RequestBody CityManagementRequestDTO cityManagementRequestDTO){
        return receiveRequestAPIService.getCheckingCertificate(cityManagementRequestDTO);
    }

    @PostMapping("/askOfficer")
    @AuthorizeRequest
    public ResponseEntity<?> askOfficerToMorgueRequest(@RequestBody MorgueRequestDTO morgueRequestDTO){
        return receiveRequestAPIService.askPoliceOfficerForMorgue(morgueRequestDTO);
    }

    @PostMapping("/blockingAccount")
    @AuthorizeRequest
    public ResponseEntity<?> accountBlockingFromMorgueRequest(@RequestBody MorgueRequestDTO morgueRequestDTO){
        return receiveRequestAPIService.getRequestToBlockAccountFromMorgue(morgueRequestDTO);
    }

    @GetMapping("/morgue")
    public ResponseEntity<CustomPage<MorgueResponseDTO>> getAllMorgueRequest(@RequestParam Integer page){
        CustomPage<MorgueResponseDTO> allRequests = receiveRequestAPIService.getAllRequests(page);
        return new ResponseEntity<CustomPage<MorgueResponseDTO>>(allRequests, HttpStatus.OK);
    }

}
