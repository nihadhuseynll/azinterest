package com.azinterest.userservice.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInterestResponse {

    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private boolean active;
}
