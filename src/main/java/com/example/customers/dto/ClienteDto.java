package com.example.customers.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class ClienteDto {

    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;

    public ClienteDto(String nombre, String direccion, String telefono, String contrasena, boolean estado) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", estado=" + estado +
                '}';
    }
}
