package com.example.smartcity.controller;

import com.example.smartcity.payload.requestDTO.CityManagementRequestDTO;
import com.example.smartcity.payload.requestDTO.MorgueRequestDTO;
import com.example.smartcity.security.hmac.AuthorizeRequest;
import com.example.smartcity.service.impl.ReceiveRequestAPIServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> creatingCheckingCertificateRequest(@RequestBody CityManagementRequestDTO cityManagementRequestDTO){
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

}
