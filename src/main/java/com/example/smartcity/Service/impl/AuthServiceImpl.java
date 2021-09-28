package com.example.smartcity.Service.impl;

import com.example.smartcity.Entity.User;
import com.example.smartcity.Repository.UserRepository;
import com.example.smartcity.Security.JwtProvider;
import com.example.smartcity.Service.AuthService;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public ApiResponse login(LoginDTO loginDTO) {
         try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            ));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateTokenAdmin(user.getUsername());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("Password or login incorrect!", false);
        }
    }
}
