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
public class MovimientoDTO {
    //private Date fecha;
    private String numero;
    private String tipo;
    private double saldo;
    private boolean estado;
    private String valor;


}
