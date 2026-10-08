package com.king.deliveryking.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MenuRequestDTO(
        @NotBlank
        String name,

        @NotNull
        Integer price,

        @Size(max = 255)
        String description
) {
}