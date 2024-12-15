package com.onlineshop.service;

import com.onlineshop.domain.Cart;
import com.onlineshop.domain.Product;
import com.onlineshop.exceptions.CartNotFoundException;
import com.onlineshop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;

    public Cart getCart(int cartId) throws CartNotFoundException{
     return    cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException("Cart with ID " + cartId + " is not found"));

    }
    public List<Product> getProductsFromCart(int cartId) throws  CartNotFoundException{
       return cartRepository.findById(cartId)
               .map(Cart::getProductsList)
               .orElseThrow(() -> new CartNotFoundException("Cart with ID " + cartId + " is not found"));
    }
    //public Cart addProductToCart()
}
