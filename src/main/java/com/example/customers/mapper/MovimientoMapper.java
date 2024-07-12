package com.example.customers.mapper;

import com.example.customers.dto.MovimientoDto;
import com.example.customers.dto.ReporteDto;
import com.example.customers.entity.Movimiento;
import com.example.customers.util.Conversion;

public class MovimientoMapper {

    public static MovimientoDto toMovimientoDTO(Movimiento movimiento) {
        return new MovimientoDto(movimiento.getCuenta().getNumero(), movimiento.getCuenta().getTipoCuenta(),movimiento.getSaldo(),
                movimiento.getCuenta().isEstado(), movimiento.getTipo()+" de "+movimiento.getValor());
    }
    public static Movimiento toMovimiento(MovimientoDto movimientoDto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(movimientoDto.getTipoMovimiento());
        movimiento.setValor(Double.parseDouble(movimientoDto.getValor()));
        movimiento.setSaldo(movimientoDto.getSaldo());
        return movimiento;
    }

    public static ReporteDto toReporteDTO(Movimiento movimiento) {
        return new ReporteDto(Conversion.convertDateToString(movimiento.getFecha()),movimiento.getCuenta().getCliente().getNombre(),movimiento.getCuenta().getNumero(),
                movimiento.getCuenta().getTipoCuenta(), movimiento.getCuenta().getSaldoInicial(),
                movimiento.getCuenta().isEstado(), movimiento.getValor().toString(), movimiento.getSaldo());
    }
}
