package com.example.customers.exception;

public class MovimientoNotFoundException extends RuntimeException {
    public MovimientoNotFoundException(String mensaje) {
        super(mensaje);
    }
}