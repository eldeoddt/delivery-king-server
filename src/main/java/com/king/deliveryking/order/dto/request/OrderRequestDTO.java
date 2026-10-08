package com.king.deliveryking.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record OrderRequestDTO(

        @NotNull(message = "메뉴는 필수입니다.")
        Long menuId
) {
}
