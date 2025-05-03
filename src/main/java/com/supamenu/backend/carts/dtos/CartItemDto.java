package com.supamenu.backend.carts.dtos;

import com.supamenu.backend.menus.dtos.MenuItemDto;

import java.math.BigDecimal;

public record CartItemDto(
        MenuItemDto menuItem,
        Integer quantity,
        BigDecimal totalPrice) {
}
