package com.unisweets.unisweetsbackend.user.repository;

import com.unisweets.unisweetsbackend.user.model.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserClientRepository extends JpaRepository<UserClient, Long> {
    Optional<UserClient> findUserClientByUsername(String username);
}
