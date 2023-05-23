package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.User;
import com.unisweets.unisweetsbackend.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignUpMapper {
    public User mapToEntity(UserSignUpDto userSignUpDto) {
        return new User(
                UserRole.valueOf(userSignUpDto.getUserRole()),
                userSignUpDto.getUsername(),
                userSignUpDto.getEmail(),
                new BCryptPasswordEncoder().encode(userSignUpDto.getPassword()),
                userSignUpDto.getFirstName(),
                userSignUpDto.getLastName()
        );
    }
}
