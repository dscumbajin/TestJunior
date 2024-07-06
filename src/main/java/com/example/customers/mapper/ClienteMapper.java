package com.example.customers.mapper;

import com.example.customers.dto.ClienteDTO;
import com.example.customers.dto.CuentaDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {
    public static ClienteDTO toClienteDTO(Cliente cliente) {
        List<Long> cuentaIds = cliente.getCuentas().stream()
                .map(Cuenta::getCuentaId)
                .toList();

        return new ClienteDTO(cliente.getNombre(), cliente.getDireccion(),cliente.getTelefono(),cliente.getContrasena(),cliente.isEstado());
    }

    public static Cliente toCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContrasena(clienteDTO.getContrasena());
        // No establecemos las cuentas aquí, ya que estamos convirtiendo a un DTO simple
        return cliente;
    }


}
