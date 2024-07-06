package com.example.customers.validators;

import com.example.customers.entity.Cliente;
import com.example.customers.exception.ApiUnprocessableEntity;

public interface IClienteValidator {

    void validador(Cliente cliente) throws ApiUnprocessableEntity;
}
