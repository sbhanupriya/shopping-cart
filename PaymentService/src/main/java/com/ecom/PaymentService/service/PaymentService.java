package com.ecom.PaymentService.service;

import com.ecom.PaymentService.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
