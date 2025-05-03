package com.supamenu.backend.payment;

import com.supamenu.backend.payment.dtos.InvoiceRequest;
import com.supamenu.backend.payment.dtos.InvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class IremboPaymentService {

    @Autowired
    private IremboPayClient iremboPayClient;

    public InvoiceResponse createInvoice() {

        var customer = new Customer(
                "user@email.com",
                "0780862515",
                "Jixle Manzi"
        );

        var paymentItem = new PaymentItem(
                2000,
                1,
                "PC-aaf751b73f"
        );

        var invoiceRequest = new InvoiceRequest(
                "TST-1020",
                "PI-3e5fe23f2d",
                customer,
                Collections.singletonList(paymentItem),
                "Testing the payment",
                "2023-10-10T10:00:00Z",
                "en"
        );

        return iremboPayClient.createInvoice(invoiceRequest);
    }

}