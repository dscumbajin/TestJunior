package com.example.customers.service;

import com.example.customers.dto.ClienteDto;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.exception.ClienteYaExisteException;
import com.example.customers.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void save() {
        List<Cuenta> cuentas = new ArrayList<>();
        Cliente cliente = new Cliente("test",true, cuentas);
        when(clienteRepository.findByIdentificacion(cliente.getIdentificacion())).thenReturn(null);
        boolean result = clienteService.save(cliente);
        assertTrue(result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void saveExistingCliente() {
        List<Cuenta> cuentas = new ArrayList<>();
        Cliente cliente = new Cliente("test",true, cuentas);
        when(clienteRepository.findByIdentificacion(cliente.getIdentificacion()).get()).thenReturn(cliente);
        ClienteYaExisteException exception = assertThrows(ClienteYaExisteException.class,
                () -> clienteService.save(cliente));
        assertEquals("La identificación debe ser única", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    void clienteDTOs() {
        List<Cuenta> cuentas = new ArrayList<>();
        Cliente cliente1 = new Cliente("test",true, cuentas);
        Cliente cliente2 = new Cliente("test2", true, cuentas);
        List<Cliente> clients = new ArrayList<>();
        clients.add(cliente1);
        clients.add(cliente2);
        when(clienteRepository.findAll()).thenReturn(clients);
        List<ClienteDto> clienteDtos = clienteService.clienteDTOs();
        assertEquals(2, clienteDtos.size());
        assertEquals(cliente1.getNombre(), clienteDtos.get(0).getNombre());
        assertEquals(cliente2.getNombre(), clienteDtos.get(1).getNombre());
    }

    @Test
    public void findById() {
        List<Cuenta> cuentas = new ArrayList<>();
        Long idCliente = 1L;
        Cliente cliente = new Cliente("test",true, cuentas);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        ClienteDto clienteDTO = clienteService.findById(idCliente);
        assertEquals(cliente.getNombre(), clienteDTO.getNombre());
    }


    @Test
    public void delete() {
        List<Cuenta> cuentas = new ArrayList<>();
        Long idCliente = 1L;
        Cliente cliente = new Cliente("test",true, cuentas);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(cliente));
        boolean result = clienteService.delete(idCliente);
        assertTrue(result);
        verify(clienteRepository, times(1)).deleteById(idCliente);
    }

    @Test
    public void updateExistingCliente() {
        List<Cuenta> cuentas = new ArrayList<>();
        Long idCliente = 1L;
        Cliente clienteExistente = new Cliente("test",true, cuentas);
        Cliente clienteActualizado = new Cliente("test2", true, cuentas);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteExistente));
        boolean result = clienteService.update(idCliente, clienteActualizado);
        assertTrue(result);
        assertEquals(clienteActualizado.getNombre(), clienteExistente.getNombre());
        assertEquals(clienteActualizado.getGenero(), clienteExistente.getGenero());
        assertEquals(clienteActualizado.getEdad(), clienteExistente.getEdad());
        assertEquals(clienteActualizado.getDireccion(), clienteExistente.getDireccion());
        assertEquals(clienteActualizado.getTelefono(), clienteExistente.getTelefono());
        assertEquals(clienteActualizado.getContrasena(), clienteExistente.getContrasena());
        assertEquals(clienteActualizado.isEstado(), clienteExistente.isEstado());
        verify(clienteRepository, times(1)).save(clienteExistente);
    }

    @Test
    public void updateNonExistingCliente() {
        Long idCliente = 1L;
        List<Cuenta> cuentas = new ArrayList<>();
        Cliente clienteNoExistente = new Cliente("test",true, cuentas);
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());
        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class,
                () -> clienteService.update(idCliente, clienteNoExistente));
        assertEquals("Cliente no encontrado con ID: " + idCliente, exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    public void deleteNonExistingCliente() {
        Long idCliente = 1L;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());
        ClienteNotFoundException exception = assertThrows(ClienteNotFoundException.class,
                () -> clienteService.delete(idCliente));
        assertEquals("Cliente no encontrado con ID: " + idCliente, exception.getMessage());
        verify(clienteRepository, never()).deleteById(anyLong());
    }
}