package com.unisweets.unisweetsbackend.user.service;

import com.unisweets.unisweetsbackend.authentication.security.Utils;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.repository.UserClientRepository;
import com.unisweets.unisweetsbackend.user.repository.UserPastryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserClientService {
    private final UserClientRepository userClientRepository;
    private final UserPastryRepository userPastryRepository;

    public UserClient getUserClientByUsername(String username) {
        return userClientRepository.findUserClientByUsername(username).orElseThrow();
    }

    public Set<UserPastry> toggleUserLike(String clientUsername, String pastryUsername) {
        UserClient userClient = getUserClientByUsername(clientUsername);
        UserPastry userPastry = userPastryRepository.findUserPastriesByUsername(pastryUsername).orElseThrow();
        if (userClient.getEmail().equals(Utils.getCurrentUserEmail())) {
            userClient.toggleFavorites(userPastry);
            userClientRepository.save(userClient);
        }
        return userClient.getFavorites();
    }

    public Set<UserPastry> getClientFavoritesByUsername(String username){
        return getUserClientByUsername(username).getFavorites();
    }
}
