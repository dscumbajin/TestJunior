package com.example.customers.exception;

public class CuentaYaExisteException extends RuntimeException {
    public CuentaYaExisteException(String mensaje) {
        super(mensaje);
    }
}