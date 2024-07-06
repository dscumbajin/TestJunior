package com.example.customers.service;

import com.example.customers.dto.MovimientoDTO;
import com.example.customers.dto.ReporteDTO;
import com.example.customers.entity.Movimiento;

import java.util.Date;
import java.util.List;

public interface IMoviminetoServiceImpl {

    public Movimiento save(MovimientoDTO movimientoDTO);
    public Movimiento update(Long id, MovimientoDTO movimientoDTO);
    public boolean delete(Long id);
    public List<MovimientoDTO> movimientoDtos();
    public MovimientoDTO findById(Long id);
    public List<MovimientoDTO> findByCuentaNumero(String numero);
    public List<ReporteDTO> findByCuentaNumeroAndFechaBetween(String numero, String fechaInicio, String fechaFin);
}
