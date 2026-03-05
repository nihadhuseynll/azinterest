package com.azinterest.interestservice.service;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;

import java.util.List;

public interface InterestService {

    List<GetInterestResponse> getAllInterests();

    CreateInterestResponse createInterest(CreateInterestRequest createInterestRequest);
}
