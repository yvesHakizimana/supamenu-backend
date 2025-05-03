package com.supamenu.backend.payment.dtos;

public record InvoiceResponse (
        String id,
        String status,
        String transactionId
){

}