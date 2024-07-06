package com.example.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClienteDTO {

    private String nombre;
    private String direccion;
    private String telefono;
    private String contrasena;
    private boolean estado;
    //private List<Long> cuentaIds;

    public ClienteDTO(String nombre, String direccion, String telefono, String contrasena, boolean estado) {
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
