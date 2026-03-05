package com.azinterest.interestservice.exception.model;

public class InterestNotFoundException extends RuntimeException {
    public InterestNotFoundException(String message) {
        super(message);
    }
}
