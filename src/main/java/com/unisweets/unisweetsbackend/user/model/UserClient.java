package com.unisweets.unisweetsbackend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unisweets.unisweetsbackend.user.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "likedBy")
public class UserClient extends User {
    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_client_id"),
            inverseJoinColumns = @JoinColumn(name = "user_pastry_id")
    )
    @JsonIgnore
    private List<UserPastry> favorites;

    public UserClient(UserRole userRole, String username, String email, String password, String firstName, String lastName) {
        super(userRole, username, email, password, firstName, lastName);
    }

    public void toggleFavorites(UserPastry userPastry) {
        if (favorites.contains(userPastry)) {
            favorites.remove(userPastry);
        } else {
            favorites.add(userPastry);
        }
    }
}
