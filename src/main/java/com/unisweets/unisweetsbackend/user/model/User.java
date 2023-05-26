package com.unisweets.unisweetsbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unisweets.unisweetsbackend.announcement.model.Location;
import com.unisweets.unisweetsbackend.picture.Picture;
import com.unisweets.unisweetsbackend.user.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "user_basic")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    //    @Column(nullable = false, unique = true)
    private String username;

    //    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @Nationalized
    private String firstName;

    @Column(nullable = false)
    @Nationalized
    private String lastName;

    @ManyToOne
    private Picture userPicture;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDate signUpDate;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDate lastSignInDate;

    @ManyToOne
    private Location location;

    public User(UserRole userRole, String username, String email, String password, String firstName, String lastName) {
        this.userRole = userRole;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.signUpDate = LocalDate.now();
        this.lastSignInDate = this.signUpDate;
    }
}
