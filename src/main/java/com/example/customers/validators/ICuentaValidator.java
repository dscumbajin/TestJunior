package com.example.customers.validators;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.exception.ApiUnprocessableEntity;

public interface ICuentaValidator {

    void validador(CuentaDTO cuentaDTO) throws ApiUnprocessableEntity;

}
