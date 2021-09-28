package com.example.smartcity.Service;


import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;

public interface AuthService {

    ApiResponse login(LoginDTO loginDTO);
}
