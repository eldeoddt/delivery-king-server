package com.king.deliveryking.user.dto.request;

import com.king.deliveryking.global.common.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequestDTO(

        @NotBlank(message = "이름은 필수입니다.")
        @Schema(description = "로그인 아이디", example = "user1")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Schema(description = "비밀번호", example = "password1234")
        String password,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Schema(description = "이메일", example = "user1@example.com")
        String email,

        @Schema(description = "가입 역할 (기본값: CUSTOMER)", example = "CUSTOMER")
        UserRole role,

        @Schema(description = "사장님 가입 시 필요한 토큰 (role이 OWNER일 때만 사용)", example = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
        String ownerToken
) {

    // 컴팩트 생성자
    public SignupRequestDTO {
        // role이 없으면 고객으로 처리한다.
        if (role == null) {
            role = UserRole.CUSTOMER;
        }
    }
}