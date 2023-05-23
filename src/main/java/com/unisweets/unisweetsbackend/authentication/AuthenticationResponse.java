package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.User;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private final String token;
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String userPictureURL;
    private String userLocation;
    private String instagram;
    private String youtube;
    private String facebook;
    private String tiktok;

    public AuthenticationResponse(String token, User user) {
        this.token = token;
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userPictureURL = user.getUserPictureURL();
        this.userLocation = user.getLocation() == null ? null : user.getLocation().getLocationName();
        this.instagram = user.getInstagram();
        this.youtube = user.getYoutube();
        this.facebook = user.getFacebook();
        this.tiktok = user.getTiktok();
    }
}
