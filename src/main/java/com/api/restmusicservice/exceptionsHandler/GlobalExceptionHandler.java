package com.api.restmusicservice.exceptionsHandler;

import com.api.restmusicservice.exceptions.InvalidPageRequestException;
import com.api.restmusicservice.exceptions.MusicDataNotFoundException;
import com.api.restmusicservice.exceptions.NoContainsKeyException;
import com.api.restmusicservice.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidPageRequestException.class)
    public ResponseEntity<String> handleInvalidPageRequestException(InvalidPageRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MusicDataNotFoundException.class)
    public ResponseEntity<String> handleMusicNotFoundException(MusicDataNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoContainsKeyException.class)
    public ResponseEntity<String> handleNoContainsKeyException(NoContainsKeyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
