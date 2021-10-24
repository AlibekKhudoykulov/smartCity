package com.example.smartcity.service;

import com.example.smartcity.payload.responseDTO.CrimeResponseDTO;
import com.example.smartcity.payload.responseDTO.CustomPage;
import org.springframework.http.ResponseEntity;


public interface HTTPRequestService {

    ResponseEntity<?> makePOSTHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey, String reqBody);

    ResponseEntity<?> makeGETHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey);


}
