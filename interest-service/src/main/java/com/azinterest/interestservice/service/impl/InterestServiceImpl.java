package com.azinterest.interestservice.service.impl;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.request.UpdateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.dto.response.UpdateInterestResponse;
import com.azinterest.interestservice.entity.Interest;
import com.azinterest.interestservice.exception.model.DuplicateInterestException;
import com.azinterest.interestservice.exception.model.InterestNotFoundException;
import com.azinterest.interestservice.mapper.InterestMapper;
import com.azinterest.interestservice.repository.InterestRepository;
import com.azinterest.interestservice.service.AwsS3Service;
import com.azinterest.interestservice.service.InterestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {

    private final InterestMapper interestMapper;
    private final InterestRepository interestRepository;
    private final AwsS3Service awsS3Service;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public List<GetInterestResponse> getAllInterests() {
        return interestRepository.findAll()
                .stream().map(interestMapper::toGetInterestResponse)
                .toList();
    }

    @Override
    @Transactional
    public CreateInterestResponse createInterest(CreateInterestRequest createInterestRequest, MultipartFile image) {
        try {
            log.info("Creating interest with name {}", createInterestRequest.getName());
            String imageKey = awsS3Service.uploadFile(image);
            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + imageKey;
            Interest interest = interestMapper.toInterest(createInterestRequest);
            interest.setActive(true);
            interest.setImageUrl(imageUrl);
            Interest saved = interestRepository.saveAndFlush(interest);
            return interestMapper.toCreateInterestResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateInterestException("Interest name or slug already exists");
        }
    }

    @Override
    public List<GetInterestResponse> getInterestsByIDs(Set<UUID> ids) {
        return interestRepository.findAllById(ids)
                .stream()
                .filter(Interest::isActive)
                .map(interestMapper::toGetInterestResponse)
                .toList();
    }

    @Override
    @Transactional
    public UpdateInterestResponse updateInterest(UUID id, UpdateInterestRequest updateInterestRequest) {
        Interest interest = interestRepository.findById(id)
                .orElseThrow(() -> new InterestNotFoundException("Interest not found"));

        interest.setName(updateInterestRequest.getName());
        interest.setSlug(updateInterestRequest.getSlug());
        interest.setDescription(updateInterestRequest.getDescription());
        interest.setImageUrl(updateInterestRequest.getImageUrl());

        try {
            Interest saved = interestRepository.save(interest);
            return interestMapper.toUpdateInterestResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateInterestException("Interest name or slug already exists");
        }
    }

    @Override
    @Transactional
    public void deactivateInterest(UUID id) {
        Interest interest = interestRepository.findById(id)
                .orElseThrow(() -> new InterestNotFoundException("Interest not found"));

        if (!interest.isActive()) {
            return;
        }

        interest.setActive(false);
    }
}
