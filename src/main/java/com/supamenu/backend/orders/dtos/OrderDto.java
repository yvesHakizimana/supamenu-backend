package com.supamenu.backend.orders.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String status,
        LocalDateTime createdAt,
        List<OrderItemDto> items,
        BigDecimal totalPrice
) {
}
