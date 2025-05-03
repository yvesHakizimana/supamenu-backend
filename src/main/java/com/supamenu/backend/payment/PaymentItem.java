package com.supamenu.backend.payment;

public record PaymentItem(
        Integer unitAmount,
        Integer quantity,
        String code
) {
}
