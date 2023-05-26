package com.unisweets.unisweetsbackend.user;

import com.unisweets.unisweetsbackend.picture.Picture;
import lombok.Data;

@Data
public class UserPastryDto extends UserDto {
    private String about;
    private String instagram;
    private String facebook;
    private String youtube;
    private String tiktok;

    public UserPastryDto(String username,
                         String email,
                         String password,
                         String firstName,
                         String lastName,
                         Picture userPicture,
                         String location,
                         String about,
                         String instagram,
                         String facebook,
                         String youtube,
                         String tiktok) {
        super(username, email, password, firstName, lastName, userPicture, location);
        this.about = about;
        this.instagram = instagram;
        this.facebook = facebook;
        this.youtube = youtube;
        this.tiktok = tiktok;
    }
}
