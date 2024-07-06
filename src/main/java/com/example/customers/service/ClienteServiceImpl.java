package com.example.customers.service;

import com.example.customers.dto.ClienteDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.exception.ClienteYaExisteException;
import com.example.customers.mapper.ClienteMapper;
import com.example.customers.repository.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ClienteServiceImpl implements IClienteServiceImpl{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean save(Cliente cliente) {
        Cliente clientReq = clienteRepository.findByIdentificacion(cliente.getIdentificacion());
        if (clientReq != null) {
            throw new ClienteYaExisteException("La identificación debe ser única");
        } else {
            clienteRepository.save(cliente);
            return true;
        }
    }

    @Override
    public boolean update(Long id, Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        } else {
            Cliente cliente = clienteOptional.get();
                cliente.setNombre(clienteDetails.getNombre());
                cliente.setGenero(clienteDetails.getGenero());
                cliente.setEdad(clienteDetails.getEdad());
                cliente.setDireccion(clienteDetails.getDireccion());
                cliente.setTelefono(clienteDetails.getTelefono());
                cliente.setContrasena(clienteDetails.getContrasena());
                cliente.setEstado(clienteDetails.isEstado());
                clienteRepository.save(cliente);
            }
        return true;
    }

    @Override
    public boolean delete(Long id) {

        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cliente no encontrado con ID: " + id);
        } else {
            clienteRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<ClienteDTO> clienteDTOs() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ClienteMapper::toClienteDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado con ID: " + id));
        return ClienteMapper.toClienteDTO(cliente);
    }
}

