package com.example.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {
    private String numero;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private String nombre;
}
