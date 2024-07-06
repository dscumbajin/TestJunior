package com.example.customers.exception;

public class ClienteYaExisteException extends RuntimeException {
    public ClienteYaExisteException(String mensaje) {
        super(mensaje);
    }
}