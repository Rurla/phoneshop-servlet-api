package com.es.phoneshop.exception;

public class InvalidNumberException extends RuntimeException {
    public InvalidNumberException() {
        super("Invalid number.");
    }
}
