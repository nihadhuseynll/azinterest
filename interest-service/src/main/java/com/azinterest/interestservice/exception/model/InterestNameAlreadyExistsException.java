package com.azinterest.interestservice.exception.model;

public class InterestNameAlreadyExistsException extends RuntimeException {
    public InterestNameAlreadyExistsException(String message) {
        super(message);
    }
}
