package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.User;
import com.example.smartcity.Repository.UserRepository;
import com.example.smartcity.Service.UserService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return new ApiResponse("Success",true,allUsers);
    }

    @Override
    public ApiResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
        return new ApiResponse("Success",true,user);
    }

    @Override
    public ApiResponse addUser(UserDTO userDTO) {
        Optional<User> byUsername = userRepository.findByUsername(userDTO.getUsername());
        if (byUsername.isPresent()) throw new RestException("Username already exist",HttpStatus.CONFLICT);

        Optional<User> byCardNumber = userRepository.findByCardNumber(userDTO.getCardNumber());
        if (byCardNumber.isPresent()) throw new RestException("Already registered with this card Number",HttpStatus.CONFLICT);

        User user=new User(
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getPhoneNumber(),
                userDTO.getCardNumber(),
                userDTO.getEmail(),
                userDTO.getRoles()
        );

        userRepository.save(user);
        return new ApiResponse("Saved Successfully",true);
    }

    @Override
    public ApiResponse editUser(UUID id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));

        boolean b = userRepository.existsByUsernameAndIdNot(
                userDTO.getUsername(),
                id
        );

        if (b) throw new RestException("Username already existed",HttpStatus.CONFLICT);

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        userRepository.save(user);

        return new ApiResponse("Edited Successfully",true);
    }

    @Override
    public ApiResponse deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return new ApiResponse("Successfully deleted", true);
        } catch (Exception e) {
            throw new RestException("User Not found", HttpStatus.NOT_FOUND);
        }
    }
}
