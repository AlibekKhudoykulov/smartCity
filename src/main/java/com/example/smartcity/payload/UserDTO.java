package com.example.smartcity.payload;

import com.example.smartcity.Entity.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    @NotBlank(message = "CardNumber must not be null")
    private long cardNumber;

    @NotBlank(message = "Username must not be null")
    private String username;

    @NotBlank(message = "Password must not be null")
    private String password;

    @NotBlank(message = "roleId must not be null")
    private Set<Role> roles;

    private String email;

    private String phoneNumber;

    public UserDTO(long cardNumber, String username, String password, Set<Role> roles) {
        this.cardNumber = cardNumber;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username, String password, String email, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
