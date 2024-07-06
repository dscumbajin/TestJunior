package com.example.customers.mapper;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.entity.Cuenta;

public class CuentaMapper {

    public static CuentaDTO toCuentaDTO(Cuenta cuenta) {
        return new CuentaDTO(cuenta.getNumero(), cuenta.getTipoCuenta(), cuenta.getSaldoInicial(), cuenta.isEstado(), cuenta.getCliente().getNombre());
    }
    public static Cuenta toCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero(cuentaDTO.getNumero());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.isEstado());
        return cuenta;
    }
}
