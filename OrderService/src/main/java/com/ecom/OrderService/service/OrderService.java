package com.ecom.OrderService.service;

import com.ecom.OrderService.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
}
