package com.example.customers.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDto {
    public String fecha;
    public String cliente;
    public String numeroCuenta;
    public String tipo;
    public double saldoInicial;
    public boolean estado;
    public String movimiento;
    public double saldoDisponible;

}
