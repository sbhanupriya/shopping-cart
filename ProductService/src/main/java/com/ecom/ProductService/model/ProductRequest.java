package com.ecom.ProductService.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private long price;
    private int quantity;
}
