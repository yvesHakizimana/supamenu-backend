package com.supamenu.backend.payment;

import com.supamenu.backend.payment.dtos.InvoiceRequest;
import com.supamenu.backend.payment.dtos.InvoiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "irembo-payment-client",
        url = "https://api.sandbox.irembopay.com",
        configuration = IremboPayFeignConfig.class
)
public interface IremboPayClient {

    @PostMapping("/payments/invoices")
    InvoiceResponse createInvoice(@RequestBody InvoiceRequest request);
}
