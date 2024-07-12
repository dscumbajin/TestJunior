package com.example.customers.validators;

import com.example.customers.dto.CuentaDto;
import com.example.customers.exception.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

@Service
public class CuentaValidatorImpl implements ICuentaValidator{

    @Override
    public void validador(CuentaDto cuentaDTO) throws ApiUnprocessableEntity {
        if(cuentaDTO.getNumero() == null || cuentaDTO.getNumero() .isEmpty()){
            throw new ApiUnprocessableEntity("El numero de cuenta es requerido");
        }
        if(cuentaDTO.getTipoCuenta() == null || cuentaDTO.getTipoCuenta() .isEmpty()){
            throw new ApiUnprocessableEntity("El tipo de cuenta es requerido");
        }
        if(cuentaDTO.getCliente() == null || cuentaDTO.getCliente().isEmpty()){
            throw new ApiUnprocessableEntity("El nombre del cliente es requerido");
        }
    }
}
