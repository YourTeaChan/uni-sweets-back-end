package com.unisweets.unisweetsbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisweets.unisweetsbackend.comment.model.Comment;
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
@EqualsAndHashCode(exclude = {"likedBy", "comments"})
public class UserPastry extends User {
    private String about;
    private String instagram;
    private String youtube;
    private String facebook;
    private String tiktok;
    @JsonIgnore
    @ManyToMany(mappedBy = "favorites", fetch = FetchType.LAZY)
    private Set<UserClient> likedBy;
    @JsonIgnore
    @OneToMany
    private List<Comment> comments;

    public UserPastry(UserRole userRole, String username, String email, String password, String firstName, String lastName) {
        super(userRole, username, email, password, firstName, lastName);
        comments = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
