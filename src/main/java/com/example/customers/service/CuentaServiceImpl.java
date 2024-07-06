package com.example.customers.service;

import com.example.customers.dto.CuentaDTO;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.exception.CuentaYaExisteException;
import com.example.customers.mapper.CuentaMapper;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements ICunetaServiceImpl {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean save(CuentaDTO cuentaDTO) {
        Cuenta cuentaReq = cuentaRepository.findByNumero(cuentaDTO.getNumero());
        if (cuentaReq != null) {
            throw new CuentaYaExisteException("El número de cuenta debe ser único");
        } else {
            Cliente cliente = clienteRepository.findByNombre(cuentaDTO.getNombre());
            if (cliente != null) {
                Cuenta cuenta = CuentaMapper.toCuenta(cuentaDTO);
                cuenta.setCliente(cliente);
                cuentaRepository.save(cuenta);
                return true;
            } else {
                throw new ClienteNotFoundException("Cliente no encontrado");
            }
        }
    }

    @Override
    public boolean update(Long id, CuentaDTO cuentaDTO) {
        Optional<Cuenta> clienteOptional = cuentaRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new CuentaNotFoundException("No existe la cuenta con el ID:  " + id);
        } else {
            Cuenta cuenta = clienteOptional.get();
            cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
            cuenta.setEstado(cuentaDTO.isEstado());
            cuentaRepository.save(cuenta);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Optional<Cuenta> clienteOptional = cuentaRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new ClienteNotFoundException("Cuenta no encontrada con ID: " + id);
        } else {
            cuentaRepository.deleteById(id);
            return true;
        }
    }

    @Override
    public List<CuentaDTO> cuentaDtos() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream()
                .map(CuentaMapper::toCuentaDTO)
                .toList();
    }

    @Override
    public CuentaDTO findById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrado con ID: " + id));
        return CuentaMapper.toCuentaDTO(cuenta);
    }
}
