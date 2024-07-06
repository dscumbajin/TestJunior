package com.example.customers.service;

import com.example.customers.dto.MovimientoDTO;

import java.util.List;

public interface IMoviminetoServiceImpl {

    public boolean save(MovimientoDTO movimientoDTO);
    public boolean update(Long id, MovimientoDTO movimientoDTO);
    public boolean delete(Long id);
    public List<MovimientoDTO> movimientoDtos();
    public MovimientoDTO findById(Long id);
}
