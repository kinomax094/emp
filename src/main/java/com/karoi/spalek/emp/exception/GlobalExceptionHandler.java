package com.karoi.spalek.emp.exception;

import com.karoi.spalek.emp.dto.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ValidationResponse> handleCountryNotFoundExceptions(
            CountryNotFoundException ex) {

        return ResponseEntity.badRequest().body(new ValidationResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()));
    }

    @ExceptionHandler(ProductComplaintNotFounException.class)
    public ResponseEntity<ValidationResponse> handleProductComplaintNotFoundExceptions(
            ProductComplaintNotFounException ex) {

        return ResponseEntity.badRequest().body(new ValidationResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()));
    }
}
