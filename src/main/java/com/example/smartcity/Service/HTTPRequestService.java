package com.example.smartcity.Service;

import org.springframework.http.ResponseEntity;


public interface HTTPRequestService {
    /**
     * To send POST request to external API
     * @param keyId Key between two components(POLICE)
     * @param action definition of action (give_info)
     * @param path request path (http://localhost:8080/api/v1/request/police)
     * @param secretKey secret key (secret key for POLICE component)
     * @param reqBody request body (request body is used when using POST method, It must be String JSON format and its fields must be acceptable)
     * @return Result of request
     */
    ResponseEntity<?> makePOSTHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey, String reqBody);


    /**
     * To send GET request to external API
     * @param keyId Component name (HOTEL)
     * @param action definition of action (get_citizen_info)
     * @param path request path (http://localhost:8080/api/v1/resident/card/{cardNumber})
     * @param secretKey secret key (secret key for HOTEL component)
     * @return Result of request
     */
    ResponseEntity<?> makeGETHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey);
}
