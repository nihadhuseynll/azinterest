package com.azinterest.interestservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {

    String uploadFile(MultipartFile file);

    byte[] downloadFile(String fileName);

    void deleteFile(String fileName);
}
