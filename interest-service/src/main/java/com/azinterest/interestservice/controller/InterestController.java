package com.azinterest.interestservice.controller;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.request.UpdateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.dto.response.UpdateInterestResponse;
import com.azinterest.interestservice.service.InterestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @GetMapping("/by-ids")
    public ResponseEntity<List<GetInterestResponse>> getInterestsByIds(@RequestParam Set<UUID> ids) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.getInterestsByIDs(ids));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateInterestResponse> updateInterest(@PathVariable UUID id,
                                                                 @RequestBody UpdateInterestRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(interestService.updateInterest(id,request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> deactivateInterest(@PathVariable UUID id) {
        interestService.deactivateInterest(id);
        return ResponseEntity.noContent().build();
    }
}
