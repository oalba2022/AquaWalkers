package com.example.aquawalkers.exceptions;

import com.example.aquawalkers.exceptions.dto.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ShoeExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(ShoeNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<ExceptionMessage> shoeNotFoundException(ShoeNotFoundException exception){
                ExceptionMessage message = new ExceptionMessage(HttpStatus.NOT_FOUND, exception.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
}
