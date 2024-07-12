package com.example.customers.service;

import com.example.customers.dto.MovimientoDto;
import com.example.customers.dto.ReporteDto;
import com.example.customers.entity.Movimiento;

import java.util.List;

public interface IMoviminetoServiceImpl {

    public Movimiento save(MovimientoDto movimientoDto);
    public Movimiento update(Long id, MovimientoDto movimientoDto);
    public boolean delete(Long id);
    public List<MovimientoDto> movimientoDtos();
    public MovimientoDto findById(Long id);
    public List<MovimientoDto> findByCuentaNumero(String numero);
    public List<ReporteDto> findByCuentaNumeroAndFechaBetween(String numero, String fechaInicio, String fechaFin);
}
