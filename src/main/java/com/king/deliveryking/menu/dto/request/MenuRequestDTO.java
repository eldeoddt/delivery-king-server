package com.king.deliveryking.menu.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MenuRequestDTO(
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        @Schema(description = "메뉴 이름", example = "짜장면")
        String name,

        @Min(value = 1, message = "가격은 1원 이상이어야 합니다.") // 최소 가격 1원
        @NotNull(message = "가격은 필수입니다.") // min에서 null을 잡지 못함
        @Schema(description = "가격 (1원 이상)", example = "7000")
        Integer price,

        @Size(max = 255, message = "설명은 255자 이하여야 합니다.")
        @Schema(description = "메뉴 설명 (선택)", example = "춘장을 볶아 만든 기본 짜장면", nullable = true)
        String description
) {
}
