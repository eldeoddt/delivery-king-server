package com.king.deliveryking.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (

        @NotBlank(message = "아이디는 필수입니다.")
        @Schema(description = "로그인 아이디", example = "user1")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        String password
){}
