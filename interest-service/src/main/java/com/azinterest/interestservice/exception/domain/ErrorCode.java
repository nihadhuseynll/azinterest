package com.azinterest.interestservice.exception.domain;

public class ErrorCode {
    public static final String VALIDATION_FAILED = "VALIDATION_FAILED";
    public static final String INTEREST_NOT_FOUND = "INTEREST_NOT_FOUND";
    public static final String INTEREST_ALREADY_EXISTS = "INTEREST_ALREADY_EXISTS";
    public static final String INTEREST_SLUG_ALREADY_EXISTS =  "INTEREST_SLUG_ALREADY_EXISTS";
    public static final String INTEREST_NAME_ALREADY_EXISTS =  "INTEREST_NAME_ALREADY_EXISTS";
    public static final String FILE_UPLOAD_FAILED = "FILE_UPLOAD_FAILED";
    public static final String FILE_DOWNLOAD_FAILED = "FILE_DOWNLOAD_FAILED";
    public static final String FILE_DELETE_FAILED = "FILE_DELETE_FAILED";
    public static final String INVALID_FILE = "INVALID_FILE";
    public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";
    public static final String UNSUPPORTED_FILE_TYPE = "UNSUPPORTED_FILE_TYPE";
}
