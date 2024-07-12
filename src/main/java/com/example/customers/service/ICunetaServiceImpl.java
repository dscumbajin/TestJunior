package com.example.customers.service;

import com.example.customers.dto.CuentaDto;

import java.util.List;

public interface ICunetaServiceImpl {

    public boolean save(CuentaDto cuentaDTO);
    public boolean update(Long id, CuentaDto cuentaDTO);
    public boolean updatelimiteDiario(CuentaDto cuentaDTO);
    public boolean delete(Long id);
    public List<CuentaDto> cuentaDtos();
    public CuentaDto findById(Long id);
}
