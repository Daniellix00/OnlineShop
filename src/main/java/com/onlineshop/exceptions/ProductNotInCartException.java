package com.onlineshop.exceptions;

public class ProductNotInCartException extends RuntimeException{
    public ProductNotInCartException(String message) {
        super(message);
    }
}
