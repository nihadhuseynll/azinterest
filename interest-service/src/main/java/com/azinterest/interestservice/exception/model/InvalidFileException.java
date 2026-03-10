package com.azinterest.interestservice.exception.model;

public class InvalidFileException extends RuntimeException {
    public InvalidFileException(String message) {
        super(message);
    }
}
