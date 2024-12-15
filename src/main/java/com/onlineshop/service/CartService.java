package com.onlineshop.service;

import com.onlineshop.domain.Cart;
import com.onlineshop.domain.Product;
import com.onlineshop.domain.User;
import com.onlineshop.exceptions.CartNotFoundException;
import com.onlineshop.exceptions.EmptyCartException;
import com.onlineshop.exceptions.ProductNotInCartException;
import com.onlineshop.repository.CartRepository;
import com.onlineshop.repository.ProductRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public Cart getCart(int cartId) throws CartNotFoundException{
     return    cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException("Cart with ID " + cartId + " is not found"));

    }
    public List<Product> getProductsFromCart(int cartId) throws  CartNotFoundException{
       return cartRepository.findById(cartId)
               .map(Cart::getProductsList)
               .orElseThrow(() -> new CartNotFoundException("Cart with ID " + cartId + " is not found"));
    }
    public void addProductToCart(Product product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getActiveCart();
        if (cart == null){
            cart = new Cart();
            cart.setUser(user);
            user.setActiveCart(cart);
        }
        cart.getProductsList().add(product);
        cartRepository.save(cart);
    }
    public void removeProductFromCart(Product product){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Cart cart = user.getActiveCart();
        if (cart.getProductsList().isEmpty()){
         throw new EmptyCartException("This cart is empty");
        }
        if (cart.getProductsList().contains(product)){
            cart.getProductsList().remove(product);
            cartRepository.save(cart);
        }else {
            throw new ProductNotInCartException("Product not in cart");
        }
    }
}
