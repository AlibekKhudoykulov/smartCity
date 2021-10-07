package com.example.smartcity.controller;

import com.example.smartcity.Service.impl.UserServiceImpl;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        ApiResponse users = userService.getAllUsers();
        return ResponseEntity.status(users.isSuccess()?200:404).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id){
        ApiResponse user = userService.getUserById(id);
        return ResponseEntity.status(user.isSuccess()?200:404).body(user);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody UserDTO userDTO){
        ApiResponse user = userService.addUser(userDTO);
        return ResponseEntity.status(user.isSuccess()?201:400).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id,@RequestBody UserDTO userDTO){
        ApiResponse user = userService.editUser(id,userDTO);
        return ResponseEntity.status(user.isSuccess()?201:400).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        ApiResponse user = userService.deleteUser(id);
        return ResponseEntity.status(user.isSuccess()?200:404).body(user);
    }
}
