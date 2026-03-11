package com.azinterest.interestservice.service;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.request.UpdateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.dto.response.UpdateInterestResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface InterestService {

    List<GetInterestResponse> getAllInterests();

    CreateInterestResponse createInterest(CreateInterestRequest createInterestRequest, MultipartFile image);

    List<GetInterestResponse> getInterestsByIDs(Set<UUID> ids);

    UpdateInterestResponse updateInterest(UUID id, UpdateInterestRequest updateInterestRequest);

    void deactivateInterest(UUID id);
}
