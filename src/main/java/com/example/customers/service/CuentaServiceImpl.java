package com.example.customers.service;

import com.example.customers.dto.CuentaDto;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.exception.ClienteNotFoundException;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.exception.CuentaYaExisteException;
import com.example.customers.mapper.CuentaMapper;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements ICunetaServiceImpl {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public boolean save(CuentaDto cuentaDTO) {
        Cuenta cuentaReq = cuentaRepository.findByNumero(cuentaDTO.getNumero());
        if (cuentaReq != null) {
            throw new CuentaYaExisteException("El número de cuenta debe ser único");
        } else {
            Cliente cliente = clienteRepository.findByNombre(cuentaDTO.getCliente());
            if (cliente != null) {
                Cuenta cuenta = CuentaMapper.toCuenta(cuentaDTO);
                cuenta.setCliente(cliente);
                cuenta.setLimiteDiario(1000);
                cuentaRepository.save(cuenta);
                return true;
            } else {
                throw new ClienteNotFoundException("Cliente no encontrado");
            }
        }
    }

    @Override
    public boolean update(Long id, CuentaDto cuentaDTO) {
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
    public boolean updatelimiteDiario(CuentaDto cuentaDTO) {
        Cuenta cuenta = cuentaRepository.findByNumero(cuentaDTO.getNumero());
        if (cuenta == null) {
            throw new CuentaNotFoundException("Cuenta no encontrada:  " + cuenta.getNumero());
        } else {
            cuenta.setLimiteDiario(cuentaDTO.getLimiteDiario());
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
    public List<CuentaDto> cuentaDtos() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        return cuentas.stream()
                .map(CuentaMapper::toCuentaDTO)
                .toList();
    }

    @Override
    public CuentaDto findById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("Cuenta no encontrado con ID: " + id));
        return CuentaMapper.toCuentaDTO(cuenta);
    }
}
