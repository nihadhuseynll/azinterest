package com.azinterest.userservice.dto.response;

import com.azinterest.userservice.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private UUID userId;
    private UUID keycloakUserId;
    private boolean onBoardingCompleted;
}
