package com.azinterest.interestservice.exception.model;

public class AwsS3FileException extends RuntimeException {
    public AwsS3FileException(String message) {
        super(message);
    }
}
