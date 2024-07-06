package com.example.customers.controller;

import com.example.customers.dto.ClienteDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        List<ClienteDTO> clienteDTOs = clienteService.clienteDTOs();
        return ResponseEntity.ok(clienteDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.findById(id);
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.save(cliente);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {

        try {
            boolean resp = clienteService.update(id, cliente);
            return new ResponseEntity<>("Cliente actualizado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
