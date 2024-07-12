package com.example.customers.service;

import com.example.customers.dto.ClienteDto;
import com.example.customers.entity.Cliente;

import java.util.List;

public interface IClienteServiceImpl {

    public boolean save(Cliente cliente);
    public boolean update(Long id, Cliente cliente);
    public boolean delete(Long id);
    public List<ClienteDto> clienteDTOs();
    public ClienteDto findById(Long id);
}
