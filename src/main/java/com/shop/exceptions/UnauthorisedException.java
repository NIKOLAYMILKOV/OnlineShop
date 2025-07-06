package com.shop.exceptions;

public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message) {
        super(message);
    }

    public UnauthorisedException(String message, Throwable cause) {
        super(message, cause);
    }
}
