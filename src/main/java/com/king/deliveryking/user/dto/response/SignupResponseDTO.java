package com.king.deliveryking.user.dto.response;

public record SignupResponseDTO(

        String username,
        String email,
        boolean owner // 사장님: 1 고객: 0
) {
}
