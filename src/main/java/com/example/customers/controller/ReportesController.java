package com.example.customers.controller;

import com.example.customers.dto.ClienteDTO;
import com.example.customers.dto.MovimientoDTO;
import com.example.customers.dto.ReporteDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.service.ClienteServiceImpl;
import com.example.customers.service.CuentaServiceImpl;
import com.example.customers.service.MovimientoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reportes")
public class ReportesController {

    @Autowired
    private MovimientoServiceImpl movimientoService;

    @Autowired
    private ClienteServiceImpl clienteService;

    @Autowired
    private CuentaServiceImpl cuentaService;

    @GetMapping
    public ResponseEntity<?> findByCuentaNumeroAndFechaBetween(@RequestParam String numero,
                                                               @RequestParam String fechaInicio,
                                                               @RequestParam String fechaFin) {
        try {
            List<ReporteDTO> reporteDTOS = movimientoService.findByCuentaNumeroAndFechaBetween(numero, fechaInicio, fechaFin);
            return new ResponseEntity<>(reporteDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
