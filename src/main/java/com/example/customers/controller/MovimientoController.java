package com.example.customers.controller;

import com.example.customers.dto.MovimientoDto;
import com.example.customers.exception.MovimientoNotFoundException;
import com.example.customers.mapper.MovimientoMapper;
import com.example.customers.service.MovimientoServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoServiceImpl movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDto>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoService.movimientoDtos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDto>getMovimientoById(@PathVariable Long id) {
        MovimientoDto movimientoDto = movimientoService.findById(id);
        return new ResponseEntity<>(movimientoDto, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createMovimiento(@RequestBody MovimientoDto movimientoDto) {
        try {
            return new ResponseEntity<>( MovimientoMapper.toMovimientoDTO(movimientoService.save(movimientoDto)),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovimiento(@PathVariable Long id, @RequestBody MovimientoDto movimientoDto) {
        try {
            return new ResponseEntity<>(MovimientoMapper.toMovimientoDTO(movimientoService.update(id, movimientoDto)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovimiento(@PathVariable Long id) {
        try {
            movimientoService.delete(id);
            return new ResponseEntity<>("Movimiento eliminado correctamente", HttpStatus.OK);
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cuenta")
    public ResponseEntity<?> getMovimientoByNumberCuenta(@RequestParam String numero) {
        try {
        List<MovimientoDto> movimientosDTO = movimientoService.findByCuentaNumero(numero);
        return new ResponseEntity<>(movimientosDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
