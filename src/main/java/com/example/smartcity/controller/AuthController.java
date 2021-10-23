package com.example.smartcity.controller;


import com.example.smartcity.entity.User;
import com.example.smartcity.security.CurrentUser;
import com.example.smartcity.service.impl.AuthServiceImpl;
import com.example.smartcity.payload.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<?> authToken(@CurrentUser User user) {
        return authService.authToken(user);
    }
}
