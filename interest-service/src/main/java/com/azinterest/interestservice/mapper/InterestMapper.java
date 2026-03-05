package com.azinterest.interestservice.mapper;

import com.azinterest.interestservice.dto.request.CreateInterestRequest;
import com.azinterest.interestservice.dto.response.CreateInterestResponse;
import com.azinterest.interestservice.dto.response.GetInterestResponse;
import com.azinterest.interestservice.dto.response.UpdateInterestResponse;
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

    @Mapping(target = "name",source = "name")
    @Mapping(target = "slug",source = "slug")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "imageUrl",source = "imageUrl")
    Interest toInterest(CreateInterestRequest createInterestRequest);

    @Mapping(target = "name",source = "name")
    @Mapping(target = "slug",source = "slug")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "imageUrl",source = "imageUrl")
    @Mapping(target = "active",source = "active")
    @Mapping(target = "createdAt",source = "createdAt")
    @Mapping(target = "updatedAt",source = "updatedAt")
    CreateInterestResponse  toCreateInterestResponse(Interest interest);

    @Mapping(target = "name",source = "name")
    @Mapping(target = "slug",source = "slug")
    @Mapping(target = "description",source = "description")
    @Mapping(target = "imageUrl",source = "imageUrl")
    @Mapping(target = "active",source = "active")
    @Mapping(target = "createdAt",source = "createdAt")
    @Mapping(target = "updatedAt",source = "updatedAt")
    UpdateInterestResponse toUpdateInterestResponse(Interest interest);
}
