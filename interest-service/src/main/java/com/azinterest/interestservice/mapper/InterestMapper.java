package com.azinterest.interestservice.mapper;

import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.entity.Interest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InterestMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "slug", source = "slug")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "imageUrl", source = "imageUrl")
    @Mapping(target = "active", source = "active")
    GetInterestResponse toGetInterestResponse(Interest interest);
}
