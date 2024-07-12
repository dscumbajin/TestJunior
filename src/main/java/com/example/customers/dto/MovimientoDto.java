package com.example.customers.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDto {
    private String numero;
    private String tipoMovimiento;
    private double saldo;
    private boolean estado;
    private String valor;
}
