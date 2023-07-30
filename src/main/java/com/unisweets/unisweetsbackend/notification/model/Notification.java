package com.unisweets.unisweetsbackend.notification.model;

import com.unisweets.unisweetsbackend.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User targetUser;
    @ManyToOne
    private User senderUser;
    private String text;
    private Boolean isRead;
    private Instant time;

    public Notification(User targetUser, User senderUser, String text) {
        this.targetUser = targetUser;
        this.senderUser = senderUser;
        this.text = text;
        this.isRead = false;
        this.time = Instant.now();
    }
}
