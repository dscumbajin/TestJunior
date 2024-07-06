package com.example.customers.service;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.dto.MovimientoDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.entity.Movimiento;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.exception.CuentaYaExisteException;
import com.example.customers.mapper.CuentaMapper;
import com.example.customers.mapper.MovimientoMapper;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.repository.CuentaRepository;
import com.example.customers.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements IMoviminetoServiceImpl {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;


    @Override
    public boolean save(MovimientoDTO movimientoDTO) {
        return false;
    }

    @Override
    public boolean update(Long id, MovimientoDTO movimientoDTO) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<MovimientoDTO> movimientoDtos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientos.stream()
                .map(MovimientoMapper::toMovimientoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO findById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("No se encontro el movimiento con ID: " + id));
        return MovimientoMapper.toMovimientoDTO(movimiento);
    }
}
