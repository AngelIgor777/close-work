package com.api.restmusicservice.exceptions;

public class MusicDataNotFoundException extends RuntimeException {
    public MusicDataNotFoundException(String message) {
        super(message);
    }
}
