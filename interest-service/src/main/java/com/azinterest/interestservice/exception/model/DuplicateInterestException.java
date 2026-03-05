package com.azinterest.interestservice.exception.model;

public class DuplicateInterestException extends RuntimeException {
    public DuplicateInterestException(String message) {
        super(message);
    }
}
