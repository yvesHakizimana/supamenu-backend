package com.supamenu.backend.payment.dtos;

import com.supamenu.backend.payment.Customer;
import com.supamenu.backend.payment.PaymentItem;

import java.util.List;

public record InvoiceRequest(
        String transactionId,
        String paymentAccountIdentifier,
        Customer customer,
        List<PaymentItem> paymentItems,
        String description,
        String expiryAt,
        String language
) {
}
