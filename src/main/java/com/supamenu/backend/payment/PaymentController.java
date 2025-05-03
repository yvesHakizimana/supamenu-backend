package com.supamenu.backend.payment;

import com.supamenu.backend.payment.dtos.InvoiceResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
@Tag(name = "Payment")
public class PaymentController {

    private final IremboPaymentService service;

    @PostMapping("/create-invoice")
    public ResponseEntity<InvoiceResponse> createInvoice(){
        var response = service.createInvoice();
        return ResponseEntity.ok(response);
    }
}
