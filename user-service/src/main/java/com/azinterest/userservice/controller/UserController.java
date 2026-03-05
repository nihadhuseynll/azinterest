package com.azinterest.userservice.controller;

import com.azinterest.userservice.dto.request.CompleteOnboardingRequest;
import com.azinterest.userservice.dto.response.UserResponseDto;
import com.azinterest.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/azinterest/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDto me(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userService.createUserIfNotExists(keycloakId);
    }

    @PutMapping("/onboarding")
    public ResponseEntity<Void> completeOnboarding(@AuthenticationPrincipal Jwt jwt,
                                                   @RequestBody CompleteOnboardingRequest completeOnboardingRequest) {

        userService.completeOnboarding(jwt.getSubject(), completeOnboardingRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
