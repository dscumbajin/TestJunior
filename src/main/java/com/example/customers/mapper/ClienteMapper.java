package com.example.customers.mapper;

import com.example.customers.dto.ClienteDto;
import com.example.customers.entity.Cliente;

public class ClienteMapper {
    public static ClienteDto toClienteDTO(Cliente cliente) {
        return new ClienteDto(cliente.getNombre(), cliente.getDireccion(),cliente.getTelefono(),cliente.getContrasena(),cliente.isEstado());
    }

    public static Cliente toCliente(ClienteDto clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContrasena(clienteDTO.getContrasena());
        // No establecemos las cuentas aquí, ya que estamos convirtiendo a un DTO simple
        return cliente;
    }


}
