package com.example.customers.service;

import com.example.customers.dto.CuentaDTO;

import java.util.List;

public interface ICunetaServiceImpl {

    public boolean save(CuentaDTO cuentaDTO);
    public boolean update(Long id, CuentaDTO cuentaDTO);
    public boolean delete(Long id);
    public List<CuentaDTO> cuentaDtos();
    public CuentaDTO findById(Long id);
}
