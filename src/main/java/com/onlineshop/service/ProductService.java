package com.onlineshop.service;

import com.onlineshop.domain.Product;
import com.onlineshop.exceptions.ProductNotFoundException;
import com.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
private ProductRepository productRepository;
public List<Product> getAllProducts(){
    return productRepository.findAll();
}
public Product getProductById(int productId) throws ProductNotFoundException {
    return productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product with ID " + productId + "is not found"));
}
public Product saveProductInShop(Product product){
   return productRepository.save(product);
}
public void deleteProduct(int productId) throws ProductNotFoundException{
    productRepository.delete(productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Product with ID " + productId + "is not found")));
}
}
