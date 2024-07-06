package com.example.customers.mapper;

import com.example.customers.dto.MovimientoDTO;
import com.example.customers.dto.ReporteDTO;
import com.example.customers.entity.Movimiento;
import com.example.customers.util.Conversion;

public class MovimientoMapper {

    public static MovimientoDTO toMovimientoDTO(Movimiento movimiento) {
        return new MovimientoDTO(movimiento.getCuenta().getNumero(), movimiento.getCuenta().getTipoCuenta(),movimiento.getSaldo(),
                movimiento.getCuenta().isEstado(), movimiento.getTipo()+" de "+movimiento.getValor());
    }
    public static Movimiento toMovimiento(MovimientoDTO movimientoDTO) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(movimientoDTO.getTipo());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setSaldo(movimientoDTO.getSaldo());
        return movimiento;
    }

    public static ReporteDTO toReporteDTO(Movimiento movimiento) {
        return new ReporteDTO(Conversion.convertDateToString(movimiento.getFecha()),movimiento.getCuenta().getCliente().getNombre(),movimiento.getCuenta().getNumero(),
                movimiento.getCuenta().getTipoCuenta(), movimiento.getCuenta().getSaldoInicial(),
                movimiento.getCuenta().isEstado(), movimiento.getValor(), movimiento.getSaldo());
    }
}
