package com.shop.exceptions;

public class OrderFulfillmentException extends RuntimeException {
    public OrderFulfillmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderFulfillmentException(String message) {
        super(message);
    }
}
