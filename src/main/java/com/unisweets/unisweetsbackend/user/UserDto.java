package com.unisweets.unisweetsbackend.user;

import com.unisweets.unisweetsbackend.picture.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Picture userPicture;
    private String location;
}
