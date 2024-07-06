package com.example.customers.exception;

public class ApiUnprocessableEntity extends RuntimeException {
    public ApiUnprocessableEntity(String message) {
        super(message);
    }
}
