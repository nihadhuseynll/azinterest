package com.azinterest.userservice.exception.handler;

import com.azinterest.userservice.exception.domain.ErrorCode;
import com.azinterest.userservice.exception.domain.ErrorResponse;
import com.azinterest.userservice.exception.model.InterestNotFoundException;
import com.azinterest.userservice.exception.model.MinimumInterestsRequiredException;
import com.azinterest.userservice.exception.model.OnboardingAlreadyCompletedException;
import com.azinterest.userservice.exception.model.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.azinterest.userservice.exception.domain.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {

        log.warn("User doesn't exist: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errorCode(USER_NOT_FOUND)
                .message("User not found")
                .developerMessage("User not found")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MinimumInterestsRequiredException.class)
    public ResponseEntity<ErrorResponse> handleMinimumInterestsRequiredException(MinimumInterestsRequiredException ex) {

        log.warn("Minimum interests required: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorCode(ErrorCode.MINIMUM_INTERESTS_REQUIRED)
                .message("Minimum interests required")
                .developerMessage("Minimum interests required")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(OnboardingAlreadyCompletedException.class)
    public ResponseEntity<ErrorResponse> handleOnboardingAlreadyCompletedException(OnboardingAlreadyCompletedException ex) {

        log.warn("Onboarding already completed: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .errorCode(ErrorCode.ONBOARDING_ALREADY_COMPLETED)
                .message("Onboarding already completed")
                .developerMessage("Onboarding already completed")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InterestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInterestNotFoundException(InterestNotFoundException ex) {
        log.warn("Interest not found: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .errorCode(ErrorCode.INTEREST_NOT_FOUND)
                .message("Interest not found")
                .developerMessage("Interest not found")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    private String getTraceId() {
        return MDC.get("traceId") != null ? MDC.get("traceId") : UUID.randomUUID().toString();
    }
}
