package com.unisweets.unisweetsbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisweets.unisweetsbackend.comment.model.Comment;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String paymentCard;
    @JsonIgnore
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
    private List<UserClient> likedBy;
    @OneToMany
    @OrderBy("time desc")
    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("time desc")
    private List<Picture> pictures;
    @Transient
    private Float rating;

    public UserPastry(UserRole userRole, String username, String email, String password, String firstName, String lastName) {
        super(userRole, username, email, password, firstName, lastName);
        comments = new ArrayList<>();
        pictures = new ArrayList<>();
    }

    public Float getRating() {
        if (!comments.isEmpty()) {
            return comments.stream().map(Comment::getRating).mapToInt(Short::intValue).sum() / (float) comments.size();
        }
        return 0.0f;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addPicture(Picture picture) {
        pictures.add(picture);
    }

    public Picture removePicture(Long id) {
        Picture pictureToDelete = pictures
                .stream()
                .filter(picture -> picture.getPictureId().equals(id))
                .findFirst()
                .orElseThrow();

        pictures.remove(pictureToDelete);
        return pictureToDelete;
    }
}
