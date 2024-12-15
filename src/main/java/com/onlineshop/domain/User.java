package com.onlineshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String username;
    private String email;
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart activeCart;
    @OneToMany(mappedBy = "user")
    private List<Orders> orders;
    @ElementCollection(fetch = FetchType.EAGER)  // Przechowywanie ról jako lista
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))  // Tabela pomocnicza dla ról
    @Column(name = "role")  // Kolumna, która przechowuje rolę
    private List<String> roles = new ArrayList<>();



    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }
}
