package br.com.bluesense.backendjava.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.bluesense.backendjava.infra.exceptions.IncorrectRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectRequestException.class)
    public ResponseEntity<String> handleIncorrectRequestException(IncorrectRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
