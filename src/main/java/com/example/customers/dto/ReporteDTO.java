package com.example.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    public String fecha;
    public String cliente;
    public String numeroCuenta;
    public String tipo;
    public double saldoInicial;
    public boolean estado;
    public String movimiento;
    public double saldoDisponible;

}
