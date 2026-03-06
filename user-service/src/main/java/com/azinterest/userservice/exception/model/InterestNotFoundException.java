package com.azinterest.userservice.exception.model;

public class InterestNotFoundException extends RuntimeException {
    public InterestNotFoundException(String message) {
        super(message);
    }
}
