package com.unisweets.unisweetsbackend.authentication.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class Utils {
    public static String getCurrentUserEmail(){
        return (String) ((Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials()).getClaims().get("sub");
    }
}
