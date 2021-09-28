package com.example.smartcity.Controller;


import com.example.smartcity.Service.impl.AuthServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;


    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        ApiResponse apiResponse = authService.login(loginDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?200:401).body(apiResponse);
    }
}
