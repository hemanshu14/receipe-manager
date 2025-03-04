package com.receipe.manager.controller;

import com.receipe.manager.exception.RecordsNotFoundException;
import com.receipe.manager.model.ErrorObject;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        ErrorObject errorObject = new ErrorObject(String.valueOf(HttpStatus.NOT_FOUND.value()), entityNotFoundException.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RecordsNotFoundException.class})
    public ResponseEntity<ErrorObject> handleRecordsNotFoundException(RecordsNotFoundException recordsNotFoundException) {
        ErrorObject errorObject = new ErrorObject(String.valueOf(HttpStatus.NOT_FOUND.value()), recordsNotFoundException.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }
}
