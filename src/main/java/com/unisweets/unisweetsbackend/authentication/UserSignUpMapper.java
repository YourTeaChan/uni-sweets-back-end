package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.model.User;
import com.unisweets.unisweetsbackend.user.model.UserClient;
import com.unisweets.unisweetsbackend.user.model.UserPastry;
import com.unisweets.unisweetsbackend.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignUpMapper {
    public UserClient mapToClient(UserSignUpDto userSignUpDto) {
        return new UserClient(
                UserRole.valueOf(userSignUpDto.getUserRole()),
                userSignUpDto.getUsername(),
                userSignUpDto.getEmail(),
                new BCryptPasswordEncoder().encode(userSignUpDto.getPassword()),
                userSignUpDto.getFirstName(),
                userSignUpDto.getLastName()
        );
    }

    public UserPastry mapToPastry(UserSignUpDto userSignUpDto) {
        return new UserPastry(
                UserRole.valueOf(userSignUpDto.getUserRole()),
                userSignUpDto.getUsername(),
                userSignUpDto.getEmail(),
                new BCryptPasswordEncoder().encode(userSignUpDto.getPassword()),
                userSignUpDto.getFirstName(),
                userSignUpDto.getLastName()
        );
    }

    public User mapToEntity(UserSignUpDto userSignUpDto) {
        if (UserRole.valueOf(userSignUpDto.getUserRole()).equals(UserRole.ROLE_PASTRY)) {
            return mapToPastry(userSignUpDto);
        }else {
            return mapToClient(userSignUpDto);
        }
    }
}
