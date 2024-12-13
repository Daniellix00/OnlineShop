package com.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDto {
    private int id;
    private int orderNumber;
    private User user;
    private List<Product> products;
    private boolean active;
}
