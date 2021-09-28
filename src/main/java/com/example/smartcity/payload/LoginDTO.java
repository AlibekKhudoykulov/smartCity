package com.example.smartcity.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull(message = "Username must not be null!")
    private String username;

    @NotNull(message = "Password must not be null!")
    private String password;
}
