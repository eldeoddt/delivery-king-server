package com.king.deliveryking.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequestDTO(

        @NotBlank(message = "이름은 필수입니다.")
        String username,
        @NotBlank(message = "이름은 필수입니다.")
        String password,
        @NotBlank(message = "이름은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,
        @NotNull
        boolean owner, // 사장님: 1 고객: 0
        String ownerToken
) {

    // 컴팩트 생성자
    public SignupRequestDTO {
        // role이 null이거나 비어있으면 기본값 세팅
        if (ownerToken == null || ownerToken.isBlank()) {
            ownerToken = "";
        }

        // 변수에 재할당만 해두면, 컴파일러가 마지막에 알아서 필드에 값을 넣어줍니다.
    }
}