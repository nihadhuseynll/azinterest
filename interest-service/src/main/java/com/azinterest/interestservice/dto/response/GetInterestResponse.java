package com.azinterest.interestservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInterestResponse {

    String name;
    String slug;
    String description;
    String imageUrl;
    boolean active;
}
