package com.azinterest.interestservice.exception.handler;

import com.azinterest.interestservice.exception.domain.ErrorCode;
import com.azinterest.interestservice.exception.domain.ErrorResponse;
import com.azinterest.interestservice.exception.model.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.warn("Validation failed: {}", ex.getMessage());

        List<ErrorResponse.FieldError> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.FieldError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()
                )).toList();

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorCode(ErrorCode.VALIDATION_FAILED)
                .message("Validation failed one or more fields")
                .developerMessage("Request validation failed")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InterestNameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInterestNameAlreadyExistsException(InterestNameAlreadyExistsException ex) {

        log.warn("Interest name already exists exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .errorCode(ErrorCode.INTEREST_NAME_ALREADY_EXISTS)
                .message("Interest name already exists")
                .developerMessage("Interest name already exists")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InterestSlugAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleInterestSlugAlreadyExistsException(InterestSlugAlreadyExistsException ex) {

        log.warn("Interest slug already exists exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .errorCode(ErrorCode.INTEREST_SLUG_ALREADY_EXISTS)
                .message("Interest slug already exists")
                .developerMessage("Interest slug already exists")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DuplicateInterestException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateInterestException(DuplicateInterestException ex) {

        log.warn("Duplicate interest exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .errorCode(ErrorCode.INTEREST_ALREADY_EXISTS)
                .message("Duplicate interest already exists")
                .developerMessage("Duplicate interest already exists")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InterestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInterestNotFoundException(InterestNotFoundException ex) {

        log.warn("Interest not found exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorCode(ErrorCode.INTEREST_NOT_FOUND)
                .message("Interest not found")
                .developerMessage("Interest not found")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileException(InvalidFileException ex) {

        log.warn("Invalid file exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorCode(ErrorCode.INVALID_FILE)
                .message("Multipart file is null or empty.")
                .developerMessage("Multipart file is null or empty.")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex) {

        log.warn("File upload exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .errorCode(ErrorCode.FILE_UPLOAD_FAILED)
                .message("File upload failed.")
                .developerMessage("AWS S3 upload operation failed.")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AwsS3FileException.class)
    public ResponseEntity<ErrorResponse> handleAwsS3FIleException(AwsS3FileException ex) {

        log.warn("AwsS3FIle exception: {}", ex.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorCode(ErrorCode.UNSUPPORTED_FILE_TYPE)
                .message("Unsupported file type.")
                .developerMessage("Uploaded file type is not allowed.")
                .path(request.getRequestURI())
                .traceId(getTraceId())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private String getTraceId() {
        return MDC.get("traceId") != null ? MDC.get("traceId") : UUID.randomUUID().toString();
    }
}
