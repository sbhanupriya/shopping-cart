package com.ecom.OrderService.external.client;

import com.ecom.OrderService.exception.CustomException;
import com.ecom.OrderService.external.request.PaymentRequest;
import com.ecom.OrderService.model.OrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ClientService {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProductService productService;

    @CircuitBreaker(name="externalProduct", fallbackMethod = "fallback")
    public void callProductService(OrderRequest orderRequest){
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
    }

    @CircuitBreaker(name="externalPayment", fallbackMethod = "fallback")
    public void callPaymentService(PaymentRequest paymentRequest){
        log.info("Payment Service is called");
        paymentService.doPayment(paymentRequest);
    }

    public void fallback(PaymentRequest paymentRequest, Throwable throwable) {
        log.error("Fallback triggered for PaymentService: {}", throwable.toString());
        throw new CustomException("Payment Service is not available", "UN_AVAILABLE", 500);
    }

    public void fallback(OrderRequest orderRequest, Throwable throwable) {
        log.error("Fallback triggered for ProductService: {}", throwable.toString());
        throw new CustomException("Product Service is not available", "UN_AVAILABLE", 500);

    }
}
