package com.unisweets.unisweetsbackend.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthenticationResponse> signUpUser(@RequestBody UserSignUpDto newUser) {
        return ResponseEntity.ok(userAuthenticationService.registerNewUser(newUser));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signInUser(@RequestBody UserSignUpDto user){
        return ResponseEntity.ok(userAuthenticationService.authenticateUser(user));
    }
}
