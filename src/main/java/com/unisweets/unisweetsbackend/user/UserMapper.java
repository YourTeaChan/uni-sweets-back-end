package com.unisweets.unisweetsbackend.user;

import com.unisweets.unisweetsbackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User mapToEntity(UserDto userDto) {
        return new User(
                UserRole.valueOf(userDto.getUserRole()),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName()
        );
    }
}
