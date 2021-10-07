package com.example.smartcity.service;

import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.UserDTO;

import java.util.UUID;


public interface UserService {

    ApiResponse getUserById(UUID id);

    ApiResponse getAllUsers();

    ApiResponse addUser(UserDTO userDTO);

    ApiResponse editUser(UUID id,UserDTO userDTO);

    ApiResponse deleteUser(UUID id);
}
