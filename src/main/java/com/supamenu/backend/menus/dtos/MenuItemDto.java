package com.supamenu.backend.menus.dtos;

import com.supamenu.backend.menus.MenuCategory;

import java.math.BigDecimal;

public record MenuItemDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        MenuCategory category
        ) {
}
