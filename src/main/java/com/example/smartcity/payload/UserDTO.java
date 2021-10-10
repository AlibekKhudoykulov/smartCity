package com.example.smartcity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
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

    public UserDTO(long cardNumber, String username, String password, Integer roleId) {
        this.cardNumber = cardNumber;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
}
