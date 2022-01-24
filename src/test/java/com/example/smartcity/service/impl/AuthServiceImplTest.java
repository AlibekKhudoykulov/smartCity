package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.Role;
import com.example.smartcity.entity.User;
import com.example.smartcity.entity.enums.RoleName;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.security.JwtProvider;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthServiceImpl.class, JwtProvider.class})
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {
    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User user;
    private Role role;

    @BeforeEach
    void init() {
        role = new Role(1, RoleName.ROLE_ADMIN);
        user = new User(
                UUID.randomUUID(),
                "username",
                passwordEncoder.encode("password"),
                Set.of(role),
                true,
                true,
                true,
                true
        );
    }

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {

        Optional<User> ofResult = Optional.<User>of(user);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(user, this.authServiceImpl.loadUserByUsername("foo"));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.<User>empty());
        assertThrows(RestException.class, () -> this.authServiceImpl.loadUserByUsername("foo"));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testLogin() throws AuthenticationException {
        when(this.authenticationManager.authenticate((Authentication) any())).thenThrow(new BadCredentialsException("Msg"));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("iloveyou");
        loginDTO.setUsername("janedoe");
        ResponseEntity<?> actualLoginResult = this.authServiceImpl.login(loginDTO);
        assertEquals("<401 UNAUTHORIZED Unauthorized,ApiResponse(message=Password or login incorrect!, success=false,"
                + " object=null),[]>", actualLoginResult.toString());
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.UNAUTHORIZED, actualLoginResult.getStatusCode());
        assertEquals("Password or login incorrect!", ((ApiResponse) actualLoginResult.getBody()).getMessage());
        assertFalse(((ApiResponse) actualLoginResult.getBody()).isSuccess());
        verify(this.authenticationManager).authenticate((Authentication) any());
    }

}

