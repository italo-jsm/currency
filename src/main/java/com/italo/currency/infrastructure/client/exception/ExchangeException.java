package com.italo.currency.infrastructure.client.exception;

public class ExchangeException extends RuntimeException {
    public ExchangeException(String message) {
        super(message);
    }
}

