package com.example.smartcity.service;


import com.example.smartcity.entity.User;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> login(LoginDTO loginDTO);
    ResponseEntity<?> authToken(User user);
}
