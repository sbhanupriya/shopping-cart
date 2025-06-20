package com.ecom.PaymentService.service;

import com.ecom.PaymentService.model.PaymentRequest;
import com.ecom.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
