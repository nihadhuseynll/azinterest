package com.azinterest.userservice.exception.model;

public class OnboardingAlreadyCompletedException extends RuntimeException {
    public OnboardingAlreadyCompletedException(String message) {
        super(message);
    }
}
