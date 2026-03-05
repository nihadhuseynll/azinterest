package com.azinterest.interestservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInterestRequest {

    private String name;
    private String slug;
    private String description;
    private String imageUrl;
}
