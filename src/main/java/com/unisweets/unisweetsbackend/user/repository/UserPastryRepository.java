package com.unisweets.unisweetsbackend.user.repository;

import com.unisweets.unisweetsbackend.user.model.UserPastry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPastryRepository extends JpaRepository<UserPastry, Long> {
    Optional<UserPastry> findUserPastriesByUsername(String username);
}
