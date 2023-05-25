package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.model.User;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String token;
    private String username;
    private String firstName;
    private String lastName;
    private String userPictureURL;
    private String userLocation;

    public AuthenticationResponse(String token, User user) {
        this.token = token;
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userPictureURL = user.getUserPictureURL();
        this.userLocation = user.getLocation() == null ? null : user.getLocation().getLocationName();
    }
}
