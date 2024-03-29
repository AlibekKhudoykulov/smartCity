package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Role;
import com.example.smartcity.entity.User;
import com.example.smartcity.repository.RoleRepository;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.service.UserService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> getAllUsers(Integer page) {
        Pageable pageableAndSortedByTime = PageRequest.of(page,10, Sort.by("createdAt").descending());
        Page<User> allUsers = userRepository.findAll(pageableAndSortedByTime);
        return ResponseEntity.ok().body(new ApiResponse("Success",true,allUsers));
    }

    @Override
    public ResponseEntity<?> getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
        return ResponseEntity.ok().body(new ApiResponse("Success",true,user));
    }

    @Override
    public ResponseEntity<?> addUser(UserDTO userDTO) {
        Optional<User> byUsername = userRepository.findByUsername(userDTO.getUsername());
        if (byUsername.isPresent()) throw new RestException("Username already exist",HttpStatus.CONFLICT);

        Optional<User> byCardNumber = userRepository.findByCardNumber(userDTO.getCardNumber());
        if (byCardNumber.isPresent()) throw new RestException("Already registered with this card Number",HttpStatus.CONFLICT);

        Optional<Role> byId = roleRepository.findById(userDTO.getRoleId());
        Role role = byId.get();
        User user=new User(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getPhoneNumber(),
                userDTO.getCardNumber(),
                userDTO.getEmail(),
                new HashSet<>(Collections.singleton(role)));


        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponse("Saved Successfully",true));
    }

    @Override
    public ResponseEntity<?> editUser(UUID id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));

        boolean b = userRepository.existsByUsernameAndIdNot(
                userDTO.getUsername(),
                id
        );

        if (b) throw new RestException("Username already existed",HttpStatus.CONFLICT);

        Optional<Role> byId = roleRepository.findById(userDTO.getRoleId());
        Role role = byId.get();

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRoles(Collections.singleton(role));
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEnabled(userDTO.isEnabled());

        userRepository.save(user);

        return ResponseEntity.ok().body(new ApiResponse("Edited Successfully",true));
    }

    @Override
    public ResponseEntity<?> deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok().body(new ApiResponse("Successfully deleted", true));
        } catch (Exception e) {
            throw new RestException("User Not found", HttpStatus.NOT_FOUND);
        }
    }
}
