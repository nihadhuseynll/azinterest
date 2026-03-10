package com.azinterest.interestservice.validation;

import com.azinterest.interestservice.exception.model.InvalidFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
public class ValidateFIle {
    public static void validateFile(MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new InvalidFileException("File cannot be empty");
        }
        long maxSize = 10 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new InvalidFileException("File size exceeds 10MB limit");
        }
        if (Objects.isNull(file.getOriginalFilename())) {
            throw new InvalidFileException("File name is invalid");
        }
    }
}
