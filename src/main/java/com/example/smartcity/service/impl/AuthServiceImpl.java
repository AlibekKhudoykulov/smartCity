package com.example.smartcity.service.impl;

import com.example.smartcity.entity.Role;
import com.example.smartcity.entity.User;
import com.example.smartcity.entity.enums.RoleName;
import com.example.smartcity.payload.UserInfo;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.security.JwtProvider;
import com.example.smartcity.service.AuthService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).
                orElseThrow(() -> new RestException("Username Not Found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateTokenAdmin(user.getUsername());
            return ResponseEntity.ok().body(new ApiResponse("Token", true, token));
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Password or login incorrect!", false));
        }
    }

    @Override
    public ResponseEntity<?> authToken(User user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = false;
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Unauthorized", false));
        }
        for (Role role : user.getRoles()) {
            if (role.getRoleName()== RoleName.ROLE_ADMIN){
                isAdmin = true;
            }
        }

        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .roles(user.getRoles()).
                isAdmin(isAdmin).build();
        return ResponseEntity.ok().body(new ApiResponse("Success!", true, userInfo));
    }

}
