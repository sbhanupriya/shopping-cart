package com.ecom.OrderService.service;

import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.external.client.PaymentService;
import com.ecom.OrderService.external.client.ProductService;
import com.ecom.OrderService.external.request.PaymentRequest;
import com.ecom.OrderService.model.OrderRequest;
import com.ecom.OrderService.repository.OrderRepository;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements  OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Override
    public Long placeOrder(OrderRequest orderRequest) {
      log.info("Placing order request {}", orderRequest);

      productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

      log.info("Creating order with status CREATED");
      Order order = Order.builder()
              .amount(orderRequest.getTotalAmount())
              .orderStatus("CREATED")
              .productId(orderRequest.getProductId())
              .orderDate(Instant.now())
              .quantity(orderRequest.getQuantity())
              .build();

      orderRepository.save(order);
      log.info("Calling paymentSevice to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(orderRequest.getTotalAmount())
                .paymentMode(orderRequest.getPaymentMode())
                .referenceNumber("")
                .orderId(order.getId())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successdully, Changing order status to success ");
            orderStatus= "SUCCESS";
        } catch (Exception ex){
            log.error("Error occured in payment. Changing orderStatus to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed successfully with OrderId {}", order.getId());

        return order.getId();
    }
}
