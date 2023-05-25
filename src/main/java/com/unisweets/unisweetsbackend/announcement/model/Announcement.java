package com.unisweets.unisweetsbackend.announcement.model;

import com.unisweets.unisweetsbackend.announcement.AnnouncementState;
import com.unisweets.unisweetsbackend.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User creator;
    private Instant creationDate;
    private LocalDate deadline;
    @ManyToOne
    private Location location;
    @ManyToOne
    private DessertType dessertType;
    private Boolean isQuick;
    private Boolean isForFavourites;
    @Nationalized
    private String title;
    @Nationalized
    @Column(length = 1023)
    private String description;
    @ManyToMany
    private List<Picture> pictures;
    @ManyToOne
    private User pastry;
    @Enumerated(EnumType.STRING)
    private AnnouncementState state;

    public Announcement(User creator,
                        LocalDate deadline,
                        Location location,
                        DessertType dessertType,
                        Boolean isQuick,
                        Boolean isForFavourites,
                        String title,
                        String description,
                        List<Picture> pictures) {
        this.creator = creator;
        this.deadline = deadline;
        this.location = location;
        this.dessertType = dessertType;
        this.isQuick = isQuick;
        this.isForFavourites = isForFavourites;
        this.title = title;
        this.description = description;
        this.pictures = pictures;
        this.state = AnnouncementState.CREATED;
        this.creationDate = Instant.now();
    }
}
