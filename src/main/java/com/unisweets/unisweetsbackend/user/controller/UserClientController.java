package com.unisweets.unisweetsbackend.user.controller;

import com.unisweets.unisweetsbackend.user.UserDto;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users/clients")
public class UserClientController {
    private final UserClientService userClientService;

    @PostMapping("/{clientUsername}/liked/{pastryUsername}")
    public ResponseEntity<List<UserClient>> toggleLiked(@PathVariable String clientUsername, @PathVariable String pastryUsername) {
        return ResponseEntity.ok(userClientService.toggleUserLike(clientUsername, pastryUsername));
    }

    @GetMapping("/{username}/liked")
    public ResponseEntity<List<UserPastry>> getClientLikedByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userClientService.getClientFavoritesByUsername(username));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserClient> updateUserClient(@RequestBody UserDto userDto, @PathVariable String username) {
        return ResponseEntity.ok(userClientService.updateUserClient(userDto, username));
    }

}
