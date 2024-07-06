package com.example.customers.service;

import com.example.customers.dto.ClienteDTO;
import com.example.customers.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IClienteServiceImpl {

    public boolean save(Cliente cliente);
    public boolean update(Long id, Cliente cliente);
    public boolean delete(Long id);
    public List<ClienteDTO> clienteDTOs();
    public ClienteDTO findById(Long id);
}
