package com.azinterest.interestservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterestRequest {

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Slug cannot be null or empty")
    private String slug;

    @NotBlank(message = "Description cannot be null or empty")
    private String description;

    @NotBlank(message = "ImageUrl cannot be null or empty")
    private String imageUrl;
}
