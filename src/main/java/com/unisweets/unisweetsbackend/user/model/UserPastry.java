package com.unisweets.unisweetsbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"likedBy", "comments", "pictures"})
public class UserPastry extends User {
    private String about;
    private String instagram;
    private String facebook;
    private String youtube;
    private String tiktok;
    @JsonIgnore
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
    private Set<UserClient> likedBy;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<Picture> pictures;

    public UserPastry(UserRole userRole, String username, String email, String password, String firstName, String lastName) {
        super(userRole, username, email, password, firstName, lastName);
        comments = new ArrayList<>();
        pictures = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addPicture(Picture picture) {
        pictures.add(picture);
    }

    public void removePicture(Picture picture) {
        pictures.remove(picture);
    }
}
