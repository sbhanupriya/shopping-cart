package com.ecom.ProductService.service;

import com.ecom.ProductService.model.ProductRequest;
import com.ecom.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, long quantity);
}
