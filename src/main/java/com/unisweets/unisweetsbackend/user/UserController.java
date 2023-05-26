package com.unisweets.unisweetsbackend.user;

import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
//        return ResponseEntity.ok(userService.createUser(userDto));
//    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{username}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @PatchMapping("{username}")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, @PathVariable String username){
        return ResponseEntity.ok(userService.updateUser(userDto, username));
    }
}
