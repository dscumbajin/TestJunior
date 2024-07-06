package com.example.customers.validators;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.exception.ApiUnprocessableEntity;
import org.springframework.stereotype.Service;

@Service
public class CuentaValidatorImpl implements ICuentaValidator{

    @Override
    public void validador(CuentaDTO cuentaDTO) throws ApiUnprocessableEntity {
        if(cuentaDTO.getNumero() == null || cuentaDTO.getNumero() .isEmpty()){
            throw new ApiUnprocessableEntity("El numero de cuenta es requerido");
        }
        if(cuentaDTO.getTipoCuenta() == null || cuentaDTO.getTipoCuenta() .isEmpty()){
            throw new ApiUnprocessableEntity("El tipo de cuenta es requerido");
        }
        if(cuentaDTO.getNombre() == null || cuentaDTO.getNombre().isEmpty()){
            throw new ApiUnprocessableEntity("El nombre del cliente es requerido");
        }
    }
}
