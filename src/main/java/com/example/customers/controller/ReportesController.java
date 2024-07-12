package com.example.customers.controller;

import com.example.customers.dto.ReporteDto;
import com.example.customers.service.MovimientoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reportes")
@RequiredArgsConstructor
public class ReportesController {

    private final MovimientoServiceImpl movimientoService;

    @GetMapping
    public ResponseEntity<?> findByNombreAndFechaBetween(@RequestParam String nombre,
                                                         @RequestParam String fechaInicio,
                                                         @RequestParam String fechaFin) {
        try {
            List<ReporteDto> reporteDtos = movimientoService.findByNombreAndFechaBetween(nombre, fechaInicio, fechaFin);
            return new ResponseEntity<>(reporteDtos.isEmpty() ? "No existen movimientos del cliente "+ nombre + " en el rango de fechas: " + fechaInicio + " - " + fechaFin : reporteDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
