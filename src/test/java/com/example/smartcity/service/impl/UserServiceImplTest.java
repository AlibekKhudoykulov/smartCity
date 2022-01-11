package com.example.smartcity.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.smartcity.entity.Role;
import com.example.smartcity.entity.User;
import com.example.smartcity.entity.enums.RoleName;
import com.example.smartcity.exception.RestException;
import com.example.smartcity.payload.ApiResponse;
import com.example.smartcity.payload.UserDTO;
import com.example.smartcity.repository.RoleRepository;
import com.example.smartcity.repository.UserRepository;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testGetUserById() {
        when(this.userRepository.findById((UUID) any())).thenReturn(Optional.<User>empty());
        assertThrows(RestException.class, () -> this.userServiceImpl.getUserById(UUID.randomUUID()));
        verify(this.userRepository).findById((UUID) any());
    }

    @Test
    void testAddUser() {
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
        assertThrows(RestException.class, () -> this.userServiceImpl.addUser(new UserDTO(1L, "janedoe", "iloveyou", 123)));
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testAddUser2() {
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
        when(this.userRepository.findByCardNumber((Long) any())).thenReturn(ofResult);
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.<User>empty());
        assertThrows(RestException.class, () -> this.userServiceImpl.addUser(new UserDTO(1L, "janedoe", "iloveyou", 123)));
        verify(this.userRepository).findByCardNumber((Long) any());
        verify(this.userRepository).findByUsername((String) any());
    }

    @Test
    void testAddUser3() {
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
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.findByCardNumber((Long) any())).thenReturn(Optional.<User>empty());
        when(this.userRepository.findByUsername((String) any())).thenReturn(Optional.<User>empty());

        Role role = new Role();
        role.setId(1);
        role.setRoleName(RoleName.ROLE_ADMIN);
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findById((Integer) any())).thenReturn(ofResult);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        ResponseEntity<?> actualAddUserResult = this.userServiceImpl.addUser(new UserDTO(1L, "janedoe", "iloveyou", 123));
        assertEquals("<200 OK OK,ApiResponse(message=Saved Successfully, success=true, object=null),[]>",
                actualAddUserResult.toString());
        assertTrue(actualAddUserResult.hasBody());
        assertTrue(actualAddUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualAddUserResult.getStatusCode());
        assertEquals("Saved Successfully", ((ApiResponse) actualAddUserResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualAddUserResult.getBody()).isSuccess());
        verify(this.userRepository).findByCardNumber((Long) any());
        verify(this.userRepository).findByUsername((String) any());
        verify(this.userRepository).save((User) any());
        verify(this.roleRepository).findById((Integer) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testEditUser() {
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
        when(this.userRepository.existsByUsernameAndIdNot((String) any(), (UUID) any())).thenReturn(true);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class,
                () -> this.userServiceImpl.editUser(id, new UserDTO(1L, "janedoe", "iloveyou", 123)));
        verify(this.userRepository).existsByUsernameAndIdNot((String) any(), (UUID) any());
        verify(this.userRepository).findById((UUID) any());
    }

    @Test
    void testEditUser2() {
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

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setEmail("jane.doe@example.org");
        user1.setId(UUID.randomUUID());
        user1.setUpdatedAt(mock(Timestamp.class));
        user1.setCardNumber(1L);
        user1.setAccountNonLocked(true);
        user1.setCreatedById(UUID.randomUUID());
        user1.setCredentialsNonExpired(true);
        user1.setEnabled(true);
        user1.setPhoneNumber("4105551212");
        user1.setUpdateById(UUID.randomUUID());
        user1.setAccountNonExpired(true);
        user1.setUsername("janedoe");
        user1.setCreatedAt(mock(Timestamp.class));
        user1.setRoles(new HashSet<Role>());
        when(this.userRepository.save((User) any())).thenReturn(user1);
        when(this.userRepository.existsByUsernameAndIdNot((String) any(), (UUID) any())).thenReturn(false);
        when(this.userRepository.findById((UUID) any())).thenReturn(ofResult);

        Role role = new Role();
        role.setId(1);
        role.setRoleName(RoleName.ROLE_ADMIN);
        Optional<Role> ofResult1 = Optional.<Role>of(role);
        when(this.roleRepository.findById((Integer) any())).thenReturn(ofResult1);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        UUID id = UUID.randomUUID();
        ResponseEntity<?> actualEditUserResult = this.userServiceImpl.editUser(id,
                new UserDTO(1L, "janedoe", "iloveyou", 123));
        assertEquals("<200 OK OK,ApiResponse(message=Edited Successfully, success=true, object=null),[]>",
                actualEditUserResult.toString());
        assertTrue(actualEditUserResult.hasBody());
        assertTrue(actualEditUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualEditUserResult.getStatusCode());
        assertEquals("Edited Successfully", ((ApiResponse) actualEditUserResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualEditUserResult.getBody()).isSuccess());
        verify(this.userRepository).existsByUsernameAndIdNot((String) any(), (UUID) any());
        verify(this.userRepository).findById((UUID) any());
        verify(this.userRepository).save((User) any());
        verify(this.roleRepository).findById((Integer) any());
        verify(this.passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void testEditUser3() {
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
        when(this.userRepository.save((User) any())).thenReturn(user);
        when(this.userRepository.existsByUsernameAndIdNot((String) any(), (UUID) any())).thenReturn(false);
        when(this.userRepository.findById((UUID) any())).thenReturn(Optional.<User>empty());

        Role role = new Role();
        role.setId(1);
        role.setRoleName(RoleName.ROLE_ADMIN);
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findById((Integer) any())).thenReturn(ofResult);
        when(this.passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        UUID id = UUID.randomUUID();
        assertThrows(RestException.class,
                () -> this.userServiceImpl.editUser(id, new UserDTO(1L, "janedoe", "iloveyou", 123)));
        verify(this.userRepository).findById((UUID) any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(this.userRepository).deleteById((UUID) any());
        ResponseEntity<?> actualDeleteUserResult = this.userServiceImpl.deleteUser(UUID.randomUUID());
        assertEquals("<200 OK OK,ApiResponse(message=Successfully deleted, success=true, object=null),[]>",
                actualDeleteUserResult.toString());
        assertTrue(actualDeleteUserResult.hasBody());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualDeleteUserResult.getStatusCode());
        assertEquals("Successfully deleted", ((ApiResponse) actualDeleteUserResult.getBody()).getMessage());
        assertTrue(((ApiResponse) actualDeleteUserResult.getBody()).isSuccess());
        verify(this.userRepository).deleteById((UUID) any());
    }

    @Test
    void testDeleteUser2() {
        doThrow(new RestException("An error occurred", HttpStatus.CONTINUE)).when(this.userRepository)
                .deleteById((UUID) any());
        assertThrows(RestException.class, () -> this.userServiceImpl.deleteUser(UUID.randomUUID()));
        verify(this.userRepository).deleteById((UUID) any());
    }
}

