package com.king.deliveryking.user.dto.response;

import com.king.deliveryking.global.common.UserRole;
import com.king.deliveryking.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponseDTO(

        @Schema(description = "로그인 아이디", example = "user1")
        String username,
        @Schema(description = "이메일", example = "user1@example.com")
        String email,
        @Schema(description = "역할", example = "CUSTOMER")
        UserRole role
) {
    public static SignupResponseDTO from(User user) {
        return new SignupResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

}
