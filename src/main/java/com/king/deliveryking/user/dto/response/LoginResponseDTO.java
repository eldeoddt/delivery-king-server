package com.king.deliveryking.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
public record LoginResponseDTO(
        @Schema(description = "로그인 아이디", example = "user1")
        String username,
        @Schema(description = "JWT (Swagger의 Authorize 버튼에 그대로 입력)", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IkNVU1RPTUVSIn0.signature")
        String accessToken
) {
}
