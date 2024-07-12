package com.example.customers.controller;

import com.example.customers.dto.ClienteDto;
import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.mapper.ClienteMapper;
import com.example.customers.service.ClienteServiceImpl;
import com.example.customers.validators.ClienteValidatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/clientes")
public class ClienteController {

    private final ClienteValidatorImpl clienteValidator;

    private final ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        List<ClienteDto> clienteDtos = clienteService.clienteDTOs();
        return ResponseEntity.ok(clienteDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {
        ClienteDto clienteDTO = clienteService.findById(id);
        return new ResponseEntity<>(clienteDTO, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            clienteValidator.validador(cliente);
            clienteService.save(cliente);
            return new ResponseEntity<>(ClienteMapper.toClienteDTO(cliente), HttpStatus.CREATED);
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
