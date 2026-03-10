package com.azinterest.interestservice.service.impl;

import com.azinterest.interestservice.exception.model.FileUploadException;
import com.azinterest.interestservice.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

import static com.azinterest.interestservice.validation.ValidateFIle.validateFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    private final S3Client s3Client;

    @Override
    public String uploadFile(MultipartFile file) {

        validateFile(file);
        String fileName = generateFileName(file);
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();
            log.info("Uploading file {} to S3 bucket {}", fileName, bucketName);
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );
            log.info("File uploaded successfully: {}", fileName);
            return fileName;
        } catch (IOException ex) {
            log.error("I/O error while uploading file", ex);
            throw new FileUploadException("Failed to read file input stream");
        } catch (S3Exception e) {
            log.error("AWS S3 error during upload", e);
            throw new FileUploadException("S3 upload failed");
        }
    }

    @Override
    public byte[] downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return responseBytes.asByteArray();
    }

    @Override
    public void deleteFile(String fileName) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}
