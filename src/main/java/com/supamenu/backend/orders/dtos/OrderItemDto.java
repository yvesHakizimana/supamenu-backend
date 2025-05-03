package com.supamenu.backend.orders.dtos;

import com.supamenu.backend.menus.dtos.MenuItemDto;

import java.math.BigDecimal;

public record OrderItemDto(
        MenuItemDto menuItem,
        int quantity,
        BigDecimal totalPrice
) {
}
