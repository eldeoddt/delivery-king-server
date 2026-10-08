package com.king.deliveryking.menu.dto.response;

import com.king.deliveryking.menu.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;

public record MenuResponseDTO(

        @Schema(description = "메뉴 id", example = "1")
        Long id,
        @Schema(description = "메뉴 이름", example = "짜장면")
        String name,
        @Schema(description = "메뉴 설명", example = "춘장을 볶아 만든 기본 짜장면", nullable = true)
        String description,
        @Schema(description = "가격", example = "7000")
        Integer price
) {
    public static MenuResponseDTO from(Menu menu) {
        return new MenuResponseDTO(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice()
        );
    }
}
