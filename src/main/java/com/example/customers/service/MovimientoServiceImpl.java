package com.example.customers.service;

import com.example.customers.dto.MovimientoDTO;
import com.example.customers.dto.ReporteDTO;
import com.example.customers.entity.Cuenta;
import com.example.customers.entity.Movimiento;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.exception.MovimientoNotFoundException;
import com.example.customers.mapper.MovimientoMapper;
import com.example.customers.repository.CuentaRepository;
import com.example.customers.repository.MovimientoRepository;
import com.example.customers.util.Conversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovimientoServiceImpl implements IMoviminetoServiceImpl {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public Movimiento save(MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepository.findByNumero(movimientoDTO.getNumero());
            if (cuenta != null) {
                List<Movimiento> listMovimientos = movimientoRepository.findByCuentaNumeroOrderByFechaDesc(cuenta.getNumero());
                double saldoTotal;
                if(listMovimientos.isEmpty()){
                    if("Retiro".equals(movimientoDTO.getTipo())){
                        saldoTotal = retirar(movimientoDTO.getValor(), cuenta.getSaldoInicial());
                    }else{
                        saldoTotal = depositar(movimientoDTO.getValor(), cuenta.getSaldoInicial());
                    }
                }else {
                    if("Retiro".equals(movimientoDTO.getTipo())){
                        saldoTotal = retirar(movimientoDTO.getValor(), listMovimientos.get(0).getSaldo());
                    }else{
                        saldoTotal = depositar(movimientoDTO.getValor(), listMovimientos.get(0).getSaldo());
                    }
                }
                Movimiento movimiento = MovimientoMapper.toMovimiento(movimientoDTO);
                movimiento.setFecha(new Date());
                movimiento.setSaldo(saldoTotal);
                movimiento.setTipo(movimientoDTO.getTipo());
                movimiento.setValor(movimientoDTO.getValor());
                movimiento.setCuenta(cuenta);
                movimientoRepository.save(movimiento);
                return movimiento;
            } else {
                throw new CuentaNotFoundException("No existe la cuenta para realizar el movimiento");
            }

    }

    @Override
    public Movimiento update(Long id, MovimientoDTO movimientoDTO) {
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(id);
        if (movimientoOptional.isEmpty()) {
            throw new MovimientoNotFoundException("No existe el movimiento con el ID:  " + id);
        } else {

            Movimiento movimiento = movimientoOptional.get();
            if("Retiro".equals(movimientoDTO.getTipo())){
                movimiento.setSaldo(retirar(movimientoDTO.getValor(), movimiento.getSaldo()));
            }else{
                movimiento.setSaldo(depositar(movimientoDTO.getValor(), movimiento.getSaldo()));
            }
            movimiento.setTipo(movimientoDTO.getTipo());
            movimiento.setValor(movimientoDTO.getValor());
            movimientoRepository.save(movimiento);
            return movimiento;
        }
    }

    @Override
    public boolean delete(Long id) {
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(id);
        if (movimientoOptional.isEmpty()) {
            throw new MovimientoNotFoundException("Movimiento no encontrado con ID: " + id);
        } else {
            movimientoRepository.deleteById(id);
            return true;
        }
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
                .orElseThrow(() -> new MovimientoNotFoundException("No se encontro el movimiento con ID: " + id));
        return MovimientoMapper.toMovimientoDTO(movimiento);
    }

    @Override
    public List<MovimientoDTO> findByCuentaNumero(String numero) {
        List<MovimientoDTO> movimientosDTO = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findByCuentaNumero(numero);
                 if(!movimientos.isEmpty()){
                     for (Movimiento movimiento : movimientos) {
                         movimientosDTO.add(MovimientoMapper.toMovimientoDTO(movimiento));
                     }
                 }else {
                     throw new MovimientoNotFoundException("No se encontro el movimientos con el numero de cuenta: " + numero);
                 }
        return movimientosDTO;
    }

    @Override
    public List<ReporteDTO> findByCuentaNumeroAndFechaBetween(String numero, String fechaInicio, String fechaFin) {
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);
        List<ReporteDTO> reporteDTOS = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findByCuentaNumeroAndFechaBetween(numero, inicioFecha, finFecha);
        if(!movimientos.isEmpty()){
            for (Movimiento movimiento : movimientos) {
                reporteDTOS.add(MovimientoMapper.toReporteDTO(movimiento));
            }
        }else {
            throw new MovimientoNotFoundException("No se encontro el movimientos con el numero de cuenta : "
                    + numero +" en el rango de fechas: " + fechaInicio + " - " + fechaFin );
        }
        return reporteDTOS;
    }

    public double retirar(String valor, double saldo) {
        if (valor.contains("-")){
            valor = valor.replace("-","");
        }
        if (Double.parseDouble(valor) > saldo) {
            throw new MovimientoNotFoundException("Saldo no disponible");
        } else {
            saldo -= Double.parseDouble(valor);
            System.out.println("Retiro exitoso. Saldo restante: " + saldo);
            return saldo;
        }
    }
    public double depositar(String valor, double saldo) {
        if (Double.parseDouble(valor) > 0) {
            saldo += Double.parseDouble(valor);
            System.out.println("Depósito exitoso. Saldo actual: " + saldo);
            return saldo;
        } else {
            throw new MovimientoNotFoundException("El valor tiene que ser positivo para depositar");
        }
    }


}
