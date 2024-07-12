package com.example.customers.validators;

import com.example.customers.dto.CuentaDto;
import com.example.customers.exception.ApiUnprocessableEntity;

public interface ICuentaValidator {

    void validador(CuentaDto cuentaDTO) throws ApiUnprocessableEntity;

}
