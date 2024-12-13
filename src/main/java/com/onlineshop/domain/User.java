package com.onlineshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart activeCart;
    @OneToMany(mappedBy = "user")
    @JoinColumn(name = "order_id")
    private List<Order> orders;


}
