package com.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private int id;
    private String nameProduct;
    private String description;
    private double price;
}
