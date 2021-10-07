package com.example.smartcity.service;


import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;

public interface AuthService {

    ApiResponse login(LoginDTO loginDTO);
}
