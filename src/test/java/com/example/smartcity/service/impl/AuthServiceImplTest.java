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
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.LoginDTO;
import com.example.smartcity.repository.UserRepository;
import com.example.smartcity.security.JwtProvider;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private JwtProvider jwtProvider;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        User user = new User();
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(mock(Timestamp.class));
        user.setCardNumber(1L);
        user.setAccountNonLocked(true);
        user.setCreatedById(UUID.randomUUID());
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setUpdateById(UUID.randomUUID());
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCreatedAt(mock(Timestamp.class));
        user.setRoles(new HashSet<Role>());
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

    @Test
    void testLogin2() throws AuthenticationException {
        when(this.jwtProvider.generateTokenAdmin((String) any())).thenReturn("ABC123");

        User user = new User();
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(mock(Timestamp.class));
        user.setCardNumber(0L);
        user.setAccountNonLocked(true);
        user.setCreatedById(UUID.randomUUID());
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setUpdateById(UUID.randomUUID());
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCreatedAt(mock(Timestamp.class));
        user.setRoles(new HashSet<Role>());
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, "Credentials");

        when(this.authenticationManager.authenticate((Authentication) any())).thenReturn(testingAuthenticationToken);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("iloveyou");
        loginDTO.setUsername("janedoe");
        ResponseEntity<?> actualLoginResult = this.authServiceImpl.login(loginDTO);
        assertEquals("<200 OK OK,ApiResponse(message=Token, success=true, object=ABC123),[]>",
                actualLoginResult.toString());
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualLoginResult.getStatusCode());
        assertEquals("Token", ((ApiResponse) actualLoginResult.getBody()).getMessage());
        assertEquals("ABC123", ((ApiResponse) actualLoginResult.getBody()).getObject());
        assertTrue(((ApiResponse) actualLoginResult.getBody()).isSuccess());
        verify(this.jwtProvider).generateTokenAdmin((String) any());
        verify(this.authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void testLogin3() throws AuthenticationException {
        when(this.jwtProvider.generateTokenAdmin((String) any())).thenThrow(new BadCredentialsException("Msg"));

        User user = new User();
        user.setPassword("iloveyou");
        user.setEmail("jane.doe@example.org");
        user.setId(UUID.randomUUID());
        user.setUpdatedAt(mock(Timestamp.class));
        user.setCardNumber(0L);
        user.setAccountNonLocked(true);
        user.setCreatedById(UUID.randomUUID());
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setPhoneNumber("4105551212");
        user.setUpdateById(UUID.randomUUID());
        user.setAccountNonExpired(true);
        user.setUsername("janedoe");
        user.setCreatedAt(mock(Timestamp.class));
        user.setRoles(new HashSet<Role>());
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, "Credentials");

        when(this.authenticationManager.authenticate((Authentication) any())).thenReturn(testingAuthenticationToken);

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
        verify(this.jwtProvider).generateTokenAdmin((String) any());
        verify(this.authenticationManager).authenticate((Authentication) any());
    }
}

