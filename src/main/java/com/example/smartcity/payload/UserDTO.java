package com.example.smartcity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

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

    private boolean enabled;

}
