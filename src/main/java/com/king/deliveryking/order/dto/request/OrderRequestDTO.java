package com.king.deliveryking.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(

        @NotNull(message = "메뉴는 필수입니다.")
        @Schema(description = "주문할 메뉴 id", example = "1")
        Long menuId
) {
}
