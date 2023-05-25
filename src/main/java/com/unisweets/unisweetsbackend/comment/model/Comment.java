package com.unisweets.unisweetsbackend.comment.model;

import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @ManyToOne
    private UserClient sender;
    private Short rating;
    private String text;
    private Instant time;

    public Comment(UserClient sender, Short rating, String text) {
        this.sender = sender;
        this.rating = rating;
        this.text = text;
        this.time = Instant.now();
    }
}
