package com.es.phoneshop.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Not enough stock");
    }
}
