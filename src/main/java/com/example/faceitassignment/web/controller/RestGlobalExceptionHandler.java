package com.example.faceitassignment.web.controller;


import com.example.faceitassignment.core.exception.DishNotFoundException;
import com.example.faceitassignment.core.exception.DrinkAdditionNotFoundException;
import com.example.faceitassignment.core.exception.DrinkNotFoundException;
import com.example.faceitassignment.core.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestGlobalExceptionHandler {


    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<?> onDishNotFoundException(DishNotFoundException e) {
        ErrorResponse<?> errorResponse = new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DrinkNotFoundException.class)
    public ResponseEntity<?> onDrinkNotFoundException(DrinkNotFoundException e) {
        ErrorResponse<?> errorResponse = new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DrinkAdditionNotFoundException.class)
    public ResponseEntity<?> onDrinkAdditionNotFoundException(DrinkAdditionNotFoundException e) {
        ErrorResponse<?> errorResponse = new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> onRuntimeException(RuntimeException e) {
        ErrorResponse<?> errorResponse = new ErrorResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

}
