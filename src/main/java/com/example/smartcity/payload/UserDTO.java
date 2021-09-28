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
    private Integer roleId;

    private String email;

    private String phoneNumber;

}
