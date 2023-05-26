package com.unisweets.unisweetsbackend.user.service;

import com.unisweets.unisweetsbackend.announcement.service.LocationService;
import com.unisweets.unisweetsbackend.user.UserDto;
import com.unisweets.unisweetsbackend.user.UserMapper;
import com.unisweets.unisweetsbackend.user.repository.UserRepository;
import com.unisweets.unisweetsbackend.user.UserRole;
import com.unisweets.unisweetsbackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final LocationService locationService;


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public User updateUser(UserDto userDto, String username) {
        User userFromDb = getUserByUsername(username);
        userFromDb.setUsername(userDto.getUsername());
        userFromDb.setUserPicture(userDto.getUserPicture());
//        userFromDb.setPassword(userDto.getPassword());
        userFromDb.setFirstName(userDto.getFirstName());
        userFromDb.setLastName(userDto.getLastName());
        userFromDb.setLocation(userDto.getLocation() != null ? locationService.getLocationByName(userDto.getLocation()) : null);
        return userRepository.save(userFromDb);
    }
}
