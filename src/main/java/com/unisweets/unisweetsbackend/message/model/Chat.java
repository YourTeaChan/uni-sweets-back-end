package com.unisweets.unisweetsbackend.message.model;

import com.unisweets.unisweetsbackend.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User firstUser;
    @ManyToOne
    private User secondUser;
    @OneToMany
    private List<Message> messages;
    @Transient
    private Instant time;

    public Chat(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Instant getTime() {
        if (messages.isEmpty()) {
            return Instant.now();
        }
        return messages.get(messages.size() - 1).getTime();
    }
}
