package com.azinterest.userservice.dto.request;

import com.azinterest.userservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompleteOnboardingRequest {

    private String username;
    private Gender gender;
    private String language;
    private String country;
    private List<UUID> interestIds;
}
