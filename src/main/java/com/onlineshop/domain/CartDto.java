package com.onlineshop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CartDto {
    private int id;
    private List<Product> productsList = new ArrayList<>();
    private User user;




}
