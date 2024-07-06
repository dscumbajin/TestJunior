package com.example.customers.controller;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.entity.Cuenta;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.service.CuentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaServiceImpl cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>>  getAllCuentas() {
        return ResponseEntity.ok(cuentaService.cuentaDtos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO>getCuentaById(@PathVariable Long id) {
        CuentaDTO cuentaDTO = cuentaService.findById(id);
        return new ResponseEntity<>(cuentaDTO, HttpStatus.OK);
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCuenta(@RequestBody CuentaDTO cuentaDTO) {
        try {
            cuentaService.save(cuentaDTO);
            return new ResponseEntity<>(cuentaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCuenta(@PathVariable Long id, @RequestBody CuentaDTO cuentaDTO) {
        try {
            boolean resp = cuentaService.update(id, cuentaDTO);
            return new ResponseEntity<>("Cuenta actualizado", HttpStatus.CREATED);
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
