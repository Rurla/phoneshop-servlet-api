package com.es.phoneshop;

public enum Error {
    NOT_A_NUMBER("Please, input number."),
    NOT_ENOUGH_STOCK("Not enough stock."),
    INVALID_NUMBER("Please, input number more than 0.");

    private final String message;

    Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
