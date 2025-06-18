package com.ecom.OrderService.service;

import com.ecom.OrderService.entity.Order;
import com.ecom.OrderService.external.client.ProductService;
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
      log.info("Order placed successfully with OrderId {}", order.getId());
      return order.getId();
    }
}
