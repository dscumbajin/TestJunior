package com.example.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;
}
