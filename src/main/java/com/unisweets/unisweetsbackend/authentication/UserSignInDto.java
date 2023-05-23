package com.unisweets.unisweetsbackend.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInDto {
    private String email;
    private String password;
}
