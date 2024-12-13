package com.onlineshop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nameProduct;
    private String description;
    private double price;
    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

}
