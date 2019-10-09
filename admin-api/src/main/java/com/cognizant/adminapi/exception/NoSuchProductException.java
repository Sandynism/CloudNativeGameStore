package com.cognizant.adminapi.exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(Integer productId) {
        super("There is no such product with product id " + productId + ".");
    }
}
