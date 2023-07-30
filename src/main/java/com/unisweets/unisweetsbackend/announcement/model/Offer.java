package com.unisweets.unisweetsbackend.announcement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @JsonIgnore
//    @ManyToOne
//    private Announcement announcement;
    @ManyToOne
    private UserPastry userPastry;
    private Integer price;

    public Offer(UserPastry userPastry, Announcement announcement, Integer price) {
        this.userPastry = userPastry;
        this.price = price;
//        this.announcement = announcement;
    }
}
