package com.king.deliveryking.menu.dto.response;

import com.king.deliveryking.menu.entity.Menu;

public record MenuResponseDTO(

        Long id,
        String name,
        String description,
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
