package com.example.smartcity.Service.impl;

import com.example.smartcity.Security.hmac.HMACUtil;
import com.example.smartcity.Service.HTTPRequestService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Date;

@Service
public class HTTPRequestServiceImpl implements HTTPRequestService {

    private final HMACUtil hmacUtil;

    public HTTPRequestServiceImpl(HMACUtil hmacUtil) {
        this.hmacUtil = hmacUtil;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<?> makePOSTHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey, String reqBody) {
        String timestamp = String.valueOf(new Date().getTime() + 300000); // signature will be expired in 5 minutes
        String signature = hmacUtil.calculateHASH(keyId, timestamp, action, secretKey);
        WebClient webClient = WebClient.create();
        return webClient.post()
                .uri(new URI(path))
                .header("sm-keyId", keyId)
                .header("sm-timestamp", timestamp)
                .header("sm-action", action)
                .header("sm-signature", signature)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(reqBody))
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    @Override
    @SneakyThrows
    public ResponseEntity<?> makeGETHTTPCallUsingHMAC(String keyId, String action, String path, String secretKey) {
        String timestamp = String.valueOf(new Date().getTime() + 300000);
        String signature = hmacUtil.calculateHASH(keyId, timestamp, action, secretKey);
        WebClient webClient = WebClient.create();
            return webClient.get()
                .uri(new URI(path))
                .header("sm-keyId", keyId)
                .header("sm-timestamp", timestamp)
                .header("sm-action", action)
                .header("sm-signature", signature)
                .retrieve()
                .toEntity(String.class)
                .block();
    }
}
