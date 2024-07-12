package com.example.customers.mapper;

import com.example.customers.dto.CuentaDto;
import com.example.customers.entity.Cuenta;

public class CuentaMapper {

    public static CuentaDto toCuentaDTO(Cuenta cuenta) {
        return new CuentaDto(cuenta.getNumero(), cuenta.getTipoCuenta(), cuenta.getSaldoInicial(), cuenta.isEstado(), cuenta.getLimiteDiario(), cuenta.getCliente().getNombre());
    }
    public static Cuenta toCuenta(CuentaDto cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(cuentaDTO.getNumero());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.isEstado());
        return cuenta;
    }
}
