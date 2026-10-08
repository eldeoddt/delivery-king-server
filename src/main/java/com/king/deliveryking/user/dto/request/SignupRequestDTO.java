package com.king.deliveryking.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequestDTO(

        @NotBlank(message = "이름은 필수입니다.")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        boolean owner, // 사장님: 1 고객: 0 -> 값이 없는 경우는 고객으로 처리한다.

        String ownerToken
) {
}