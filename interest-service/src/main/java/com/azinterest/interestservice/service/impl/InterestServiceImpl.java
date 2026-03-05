package com.azinterest.interestservice.service.impl;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.mapper.InterestMapper;
import com.azinterest.interestservice.repository.InterestRepository;
import com.azinterest.interestservice.service.InterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterestServiceImpl implements InterestService {
    
    private final InterestMapper interestMapper;
    private final InterestRepository interestRepository;

    @Override
    public List<GetInterestResponse> getAllInterests() {

        return interestRepository.findAll()
                .stream().map(interestMapper::toGetInterestResponse)
                .toList();
    }

    @Override
    public CreateInterestResponse createInterest(CreateInterestRequest createInterestRequest) {
        return null;
    }
}
