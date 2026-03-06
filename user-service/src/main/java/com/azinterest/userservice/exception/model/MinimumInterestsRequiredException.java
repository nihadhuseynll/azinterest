package com.azinterest.userservice.exception.model;

public class MinimumInterestsRequiredException extends RuntimeException {
    public MinimumInterestsRequiredException(String message) {
        super(message);
    }
}
