package com.king.deliveryking.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(

        @NotNull(message = "메뉴는 필수입니다.")
        @Schema(description = "주문할 메뉴 id", example = "1")
        Long menuId,

        @NotNull(message = "수량은 필수입니다.")
        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        @Schema(description = "수량 (1 이상)", example = "2")
        Integer quantity,

        @NotBlank(message = "배송 주소는 필수입니다.")
        @Schema(description = "배송 주소", example = "서울시 강남구 테헤란로 123")
        String address
) {
}
