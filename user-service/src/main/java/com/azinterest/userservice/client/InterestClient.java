package com.azinterest.userservice.client;

import com.azinterest.userservice.client.dto.GetInterestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(
        name = "interest-service",
        url = "http://localhost:8088"
)
public interface InterestClient {

    @GetMapping("/api/v1/interests")
    List<GetInterestResponse> getInterestsByIds(@RequestParam("ids") Set<UUID> ids);
}
