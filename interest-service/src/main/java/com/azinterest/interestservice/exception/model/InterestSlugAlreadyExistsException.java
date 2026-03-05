package com.azinterest.interestservice.exception.model;

public class InterestSlugAlreadyExistsException extends RuntimeException {
    public InterestSlugAlreadyExistsException(String message) {
        super(message);
    }
}
