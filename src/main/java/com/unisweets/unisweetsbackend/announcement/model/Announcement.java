package com.unisweets.unisweetsbackend.announcement.model;

import com.unisweets.unisweetsbackend.announcement.AnnouncementState;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserClient creator;
    private Instant creationDate;
    private LocalDate deadline;
    @ManyToOne
    private Location location;
    @ManyToOne
    private DessertType dessertType;
    private Boolean isQuick;
    private Boolean notifyFavorites;
    @Nationalized
    private String title;
    @Nationalized
    @Column(length = 1023)
    private String description;
    @OneToMany
    private List<Picture> pictures;
    @OneToMany
    private List<Offer> offers;
    @ManyToOne
    private UserPastry pastry;
    @OneToOne
    private Offer finalOffer;
    @Enumerated(EnumType.STRING)
    private AnnouncementState state;
    private Boolean paid;

    public Announcement(UserClient creator,
                        LocalDate deadline,
                        Location location,
                        DessertType dessertType,
                        Boolean isQuick,
                        Boolean notifyFavorites,
                        String title,
                        String description,
                        List<Picture> pictures) {
        this.creator = creator;
        this.deadline = deadline;
        this.location = location;
        this.dessertType = dessertType;
        this.isQuick = isQuick;
        this.notifyFavorites = notifyFavorites;
        this.title = title;
        this.description = description;
        this.pictures = pictures;
        this.state = AnnouncementState.CREATED;
        this.creationDate = Instant.now();
        this.offers = new ArrayList<>();
        this.paid = false;
    }

    public void addOffer(Offer offer) {
        this.offers.add(offer);
    }

    public void removeOffer(Offer offer) {
        this.offers.remove(offer);
    }
}
