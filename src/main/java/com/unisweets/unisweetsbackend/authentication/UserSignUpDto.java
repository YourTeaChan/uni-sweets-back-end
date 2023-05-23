package com.unisweets.unisweetsbackend.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpDto {
    private String userRole;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
