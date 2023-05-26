package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.model.User;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String token;
    //    private String email;
    private String username;
    private String userRole;
//    private String lastName;
//    private String firstName;
//    private String userPictureURL;
//    private String userLocation;

    public AuthenticationResponse(String token, User user) {
        this.token = "Bearer " + token;
//        this.email = user.getEmail();
        this.username = user.getUsername();
//        this.firstName = user.getFirstName();
//        this.lastName = user.getLastName();
//        this.userPictureURL = user.getUserPictureURL();
//        this.userLocation = user.getLocation() == null ? null : user.getLocation().getLocationName();
        this.userRole = String.valueOf(user.getUserRole());
    }
}
