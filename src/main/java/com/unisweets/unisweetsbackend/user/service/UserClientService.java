package com.unisweets.unisweetsbackend.user.service;

import com.unisweets.unisweetsbackend.authentication.security.Utils;
import com.unisweets.unisweetsbackend.notification.service.NotificationService;
import com.unisweets.unisweetsbackend.user.UserDto;
import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.repository.UserClientRepository;
import com.unisweets.unisweetsbackend.user.repository.UserPastryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserClientService {
    private final UserClientRepository userClientRepository;
    private final UserPastryRepository userPastryRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    public UserClient getUserClientByUsername(String username) {
        return userClientRepository.findUserClientByUsername(username).orElseThrow();
    }

    public List<UserClient> toggleUserLike(String clientUsername, String pastryUsername) {
        UserClient userClient = getUserClientByUsername(clientUsername);
        UserPastry userPastry = userPastryRepository.findUserPastriesByUsername(pastryUsername).orElseThrow();
        Assert.state(userClient.getEmail().equals(Utils.getCurrentUserEmail()), "Toggling like forbidden");
        userClient.toggleFavorites(userPastry);
        if (userClient.getFavorites().contains(userPastry)) {
            String text = "Ви стали улюбленим кондитером для " + userClient.getFirstName() + " " + userClient.getLastName();
            notificationService.createNotification(userPastry, userClient, text);
        }
        userClientRepository.save(userClient);
        return userPastry.getLikedBy();
    }

    public List<UserPastry> getClientFavoritesByUsername(String username) {
        return getUserClientByUsername(username).getFavorites();
    }

    public UserClient updateUserClient(UserDto userDto, String username) {
        userService.updateUser(userDto, username);
        return getUserClientByUsername(username);
    }
}
