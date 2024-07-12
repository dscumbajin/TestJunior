package com.example.customers.controller;

import com.example.customers.dto.CuentaDto;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.service.CuentaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaServiceImpl cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDto>>  getAllCuentas() {
        return ResponseEntity.ok(cuentaService.cuentaDtos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDto>getCuentaById(@PathVariable Long id) {
        CuentaDto cuentaDTO = cuentaService.findById(id);
        return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCuenta(@RequestBody CuentaDto cuentaDTO) {
        try {
            cuentaService.save(cuentaDTO);
            return new ResponseEntity<>(cuentaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long id, @RequestBody CuentaDto cuentaDTO) {
        try {
            boolean resp = cuentaService.update(id, cuentaDTO);
            return new ResponseEntity<>("Cuenta actualizado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/cuenta")
    public ResponseEntity<?> updatelimiteDiario(@RequestBody CuentaDto cuentaDTO) {
        try {
            boolean resp = cuentaService.updatelimiteDiario(cuentaDTO);
            return new ResponseEntity<>("Limite diario actualizado", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable Long id) {
        try {
            cuentaService.delete(id);
            return new ResponseEntity<>("Cuenta eliminado correctamente", HttpStatus.OK);
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
