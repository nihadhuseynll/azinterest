package com.azinterest.userservice.service;

import com.azinterest.userservice.dto.request.CompleteOnboardingRequest;
import com.azinterest.userservice.dto.response.UserResponseDto;

public interface UserService {

    UserResponseDto createUserIfNotExists(String keycloakUserId);
    void completeOnboarding(String keycloakId, CompleteOnboardingRequest completeOnboardingRequest);
}
