package com.azinterest.interestservice.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String errorCode;
    private String message;
    private String developerMessage;
    private String path;
    private String traceId;
    private List<FieldError> errors;

    @Data
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String message;
    }
}
