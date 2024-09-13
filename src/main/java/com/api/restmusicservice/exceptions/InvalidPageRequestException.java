package com.api.restmusicservice.exceptions;

public class InvalidPageRequestException extends RuntimeException {
    public InvalidPageRequestException(String message) {
        super(message);
    }
}
