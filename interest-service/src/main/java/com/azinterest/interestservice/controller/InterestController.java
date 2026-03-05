package com.azinterest.interestservice.controller;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/interests")
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<CreateInterestResponse> createInterest(@Valid @RequestBody CreateInterestRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(interestService.createInterest(request));
    }

    @GetMapping
    public ResponseEntity<List<GetInterestResponse>> getInterests() {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.getAllInterests());
    }
}
