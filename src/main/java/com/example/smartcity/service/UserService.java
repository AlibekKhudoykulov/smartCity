package com.example.smartcity.service;

import com.example.smartcity.payload.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


public interface UserService {

    ResponseEntity<?> getUserById(UUID id);

    ResponseEntity<?> getAllUsers(Integer page);

    ResponseEntity<?> addUser(UserDTO userDTO);

    ResponseEntity<?> editUser(UUID id,UserDTO userDTO);

    ResponseEntity<?> deleteUser(UUID id);
}
