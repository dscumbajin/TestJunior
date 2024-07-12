package com.example.customers.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {
    private String numero;
    private String tipoCuenta;
    private double saldoInicial;
    private boolean estado;
    private double limiteDiario;
    private String cliente;
}
