package com.supamenu.backend.carts.dtos;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        List<CartItemDto> items,
        BigDecimal totalPrice
) {
}
