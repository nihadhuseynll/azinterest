package com.azinterest.interestservice.service.impl;

import com.azinterest.interestservice.exception.model.AwsS3FileException;
import com.azinterest.interestservice.exception.model.FileUploadException;
import com.azinterest.interestservice.exception.model.InvalidFileException;
import com.azinterest.interestservice.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Objects;
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
            log.info("Uploading file {} to S3 bucket {}", fileName, bucketName);

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

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
            log.error("I/O error while uploading file {}", fileName, ex);
            throw new FileUploadException("Failed to read file input stream");

        } catch (S3Exception ex) {
            log.error("AWS S3 error while uploading file {}", fileName, ex);
            throw new AwsS3FileException("S3 upload failed");

        } catch (SdkClientException ex) {
            log.error("AWS SDK client error while uploading file {}", fileName, ex);
            throw new AwsS3FileException("AWS client error during upload");
        }
    }

    @Override
    public byte[] downloadFile(String fileName) {

        if (Objects.isNull(fileName) || fileName.isBlank()) {
            throw new InvalidFileException("File name cannot be null or empty");
        }

        try {
            log.info("Downloading file {} from S3 bucket {}", fileName, bucketName);

            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);

            log.info("File downloaded successfully: {}", fileName);

            return response.asByteArray();

        } catch (NoSuchKeyException ex) {
            log.warn("File not found in S3: {}", fileName);
            throw new InvalidFileException("File not found in S3");

        } catch (S3Exception ex) {
            log.error("AWS S3 error while downloading file {}", fileName, ex);
            throw new AwsS3FileException("Failed to download file from S3");

        } catch (SdkClientException ex) {
            log.error("AWS SDK client error while downloading file {}", fileName, ex);
            throw new AwsS3FileException("AWS client error occurred");
        }
    }

    @Override
    public void deleteFile(String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank()) {
            throw new FileUploadException("File can't be empty or null");
        }
        try {
            log.info("Deleting file {} from S3 bucket {}", fileName, bucketName);
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();
            log.info("File deleted successfully: {}", fileName);
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception ex) {
            log.error("AWS S3 error while deleting file {}", fileName, ex);
            throw new AwsS3FileException("Failed to delete file from S3");
        } catch (Exception ex) {
            log.error("Unexpected error occurred while deleting file {}", fileName, ex);
            throw new FileUploadException("Unexpected error occurred while deleting file");
        }
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }
}
