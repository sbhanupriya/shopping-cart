package com.ecom.OrderService.service;

import com.ecom.OrderService.model.OrderRequest;
import com.ecom.OrderService.model.OrderResponse;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(Long orderId);
}
