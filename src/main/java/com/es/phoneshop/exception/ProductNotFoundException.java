package com.es.phoneshop.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException() {
        super("Product not found.");
    }

    public ProductNotFoundException(long id) {
        super(String.format("Product with id %d not found.", id));
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ProductNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace) {
        super(message, cause, enableSuppression, writeableStackTrace);
    }
}
