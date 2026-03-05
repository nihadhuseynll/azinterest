package com.azinterest.userservice.service.impl;

import com.azinterest.userservice.dto.request.CompleteOnboardingRequest;
import com.azinterest.userservice.dto.response.UserResponseDto;
import com.azinterest.userservice.entity.User;
import com.azinterest.userservice.mapper.UserMapper;
import com.azinterest.userservice.repository.UserRepository;
import com.azinterest.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto createUserIfNotExists(String keycloakId) {

        UUID keycloakUserId = UUID.fromString(keycloakId);
        try {
            User user = userRepository.findByKeycloakUserId(keycloakUserId)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setKeycloakUserId(keycloakUserId);
                        newUser.setOnBoardingCompleted(false);
                        return userRepository.save(newUser);
                    });
            return userMapper.toUserResponseDto(user);
        } catch (DataIntegrityViolationException ex) {
            return userMapper.toUserResponseDto(userRepository.findByKeycloakUserId(keycloakUserId).orElseThrow());
        }
    }

    @Override
    @Transactional
    public void completeOnboarding(String keycloakId, CompleteOnboardingRequest completeOnboardingRequest) {

        UUID keycloakUserId = UUID.fromString(keycloakId);

        User user = userRepository.findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isOnBoardingCompleted()) {
            throw new RuntimeException("User is already onboarding completed");
        }

        if (completeOnboardingRequest.getInterestIds().size() < 3) {
            throw new RuntimeException("At least 3 interests required");
        }

        user.setGender(completeOnboardingRequest.getGender());
        user.setLanguage(completeOnboardingRequest.getLanguage());
        user.setCountry(completeOnboardingRequest.getCountry());
        user.setOnBoardingCompleted(true);
    }
}
