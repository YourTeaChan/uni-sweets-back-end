package com.unisweets.unisweetsbackend.authentication;

import com.unisweets.unisweetsbackend.user.User;
import com.unisweets.unisweetsbackend.user.UserRepository;
import com.unisweets.unisweetsbackend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSignUpMapper userSignUpMapper;

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public Boolean emailAlreadyExist(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean usernameAlreadyExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public AuthenticationResponse registerNewUser(UserSignUpDto newUser) {
        Assert.notNull(newUser.getEmail(), "Email is null");
        Assert.notNull(newUser.getUsername(), "Username is null");
        Assert.notNull(newUser.getPassword(), "Password is null");
        Assert.state(!emailAlreadyExist(newUser.getEmail()), "User with this email already exist");
        Assert.state(!usernameAlreadyExist(newUser.getUsername()), "User with this username already exist");

        User savedUser = userRepository.save(userSignUpMapper.mapToEntity(newUser));
        String token = generateToken(savedUser);
        return new AuthenticationResponse(token, savedUser);
    }


    public AuthenticationResponse authenticateUser(UserSignUpDto user) {
        Assert.notNull(user.getEmail(), "Email is null");
        Assert.notNull(user.getPassword(), "Password is null");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        ));

        User userByEmail = userService.getUserByEmail(user.getEmail());
        return new AuthenticationResponse(generateToken(userByEmail), userByEmail);
    }

    private String generateToken(User userInformation) {
        String role = userInformation.getUserRole().name();
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .subject(userInformation.getUsername())
                .claim("role", role)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }
}
