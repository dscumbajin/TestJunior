package com.example.customers.controller;

import com.example.customers.dto.MovimientoDTO;
import com.example.customers.service.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoServiceImpl movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> getAllMovimientos() {
        return ResponseEntity.ok(movimientoService.movimientoDtos());
    }


}
