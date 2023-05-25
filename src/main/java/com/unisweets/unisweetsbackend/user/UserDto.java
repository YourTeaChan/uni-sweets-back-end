package com.unisweets.unisweetsbackend.user;

import lombok.Data;

@Data
public class UserDto {
    private String userRole;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String location;
    private String about;
    private String instagram;
    private String youtube;
    private String facebook;
    private String tiktok;
}
