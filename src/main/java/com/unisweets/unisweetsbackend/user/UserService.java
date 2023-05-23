package com.unisweets.unisweetsbackend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserDto newUserDto){
        User newUser = userMapper.mapToEntity(newUserDto);
        return userRepository.save(newUser);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
