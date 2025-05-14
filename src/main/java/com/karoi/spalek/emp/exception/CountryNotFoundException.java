package com.karoi.spalek.emp.exception;

public class CountryNotFoundException  extends RuntimeException{

    public CountryNotFoundException(String message) {
        super(message);
    }
}