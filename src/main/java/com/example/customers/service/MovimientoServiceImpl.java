package com.example.customers.service;

import com.example.customers.dto.MovimientoDto;
import com.example.customers.dto.ReporteDto;
import com.example.customers.entity.Cliente;
import com.example.customers.entity.Cuenta;
import com.example.customers.entity.Movimiento;
import com.example.customers.exception.CuentaNotFoundException;
import com.example.customers.exception.MovimientoNotFoundException;
import com.example.customers.mapper.MovimientoMapper;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.repository.CuentaRepository;
import com.example.customers.repository.MovimientoRepository;
import com.example.customers.util.Conversion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements IMoviminetoServiceImpl {

    private final CuentaRepository cuentaRepository;

    private final ClienteRepository clienteRepository;

    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento save(MovimientoDto movimientoDto) {

        Cuenta cuenta = cuentaRepository.findByNumero(movimientoDto.getNumero());
            if (cuenta != null) {
                List<Movimiento> listMovimientos = movimientoRepository.findByCuentaNumeroOrderByFechaDesc(cuenta.getNumero());
                double saldoTotal;
                LocalDate hoy = LocalDate.now();
                Date startDate = java.sql.Date.valueOf(hoy.atStartOfDay().toLocalDate());
                Date endDate = java.sql.Date.valueOf(hoy.plusDays(1).atStartOfDay().toLocalDate());
                List<Movimiento> movimientosHoy = movimientoRepository.findByCuentaNumeroAndFechaBetween(movimientoDto.getNumero(),startDate, endDate);
                double totalRetiradoHoy = movimientosHoy.stream()
                        .filter(mov -> "Retiro".equals(mov.getTipo()))
                        .mapToDouble(Movimiento::getValor)
                        .sum();

                if (totalRetiradoHoy + Double.parseDouble(coverterNegativotoPositivo(movimientoDto.getValor())) > cuenta.getLimiteDiario()) {
                    throw new MovimientoNotFoundException("Cupo diario Excedido");
                }
                if(listMovimientos.isEmpty()){
                    if("Retiro".equals(movimientoDto.getTipoMovimiento())){
                        saldoTotal = retirar(String.valueOf(movimientoDto.getValor()), cuenta.getSaldoInicial());
                    }else{
                        saldoTotal = depositar(String.valueOf(movimientoDto.getValor()), cuenta.getSaldoInicial());
                    }
                }else {
                    if("Retiro".equals(movimientoDto.getTipoMovimiento())){
                        saldoTotal = retirar(String.valueOf(movimientoDto.getValor()), listMovimientos.get(0).getSaldo());
                    }else{
                        saldoTotal = depositar(String.valueOf(movimientoDto.getValor()), listMovimientos.get(0).getSaldo());
                    }
                }
                Movimiento movimiento = MovimientoMapper.toMovimiento(movimientoDto);
                movimiento.setFecha(new Date());
                movimiento.setSaldo(saldoTotal);
                movimiento.setTipo(movimientoDto.getTipoMovimiento());
                movimiento.setValor(Double.parseDouble(coverterNegativotoPositivo(movimientoDto.getValor())));
                movimiento.setCuenta(cuenta);
                movimientoRepository.save(movimiento);
                return movimiento;
            } else {
                throw new CuentaNotFoundException("No existe la cuenta para realizar el movimiento");
            }

    }

    @Override
    public Movimiento update(Long id, MovimientoDto movimientoDto) {
        Optional<Movimiento> movimientoOptional = movimientoRepository.findById(id);
        if (movimientoOptional.isEmpty()) {
            throw new MovimientoNotFoundException("No existe el movimiento con el ID:  " + id);
        } else {
            Movimiento movimiento = movimientoOptional.get();
            if("Retiro".equals(movimientoDto.getTipoMovimiento())){
                movimiento.setSaldo(retirar(movimientoDto.getValor(), movimiento.getSaldo()));
            }else{
                movimiento.setSaldo(depositar(movimientoDto.getValor(), movimiento.getSaldo()));
            }
            movimiento.setTipo(movimientoDto.getTipoMovimiento());
            movimiento.setValor(Double.parseDouble(movimientoDto.getValor()));
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
    public List<MovimientoDto> movimientoDtos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientos.stream()
                .map(MovimientoMapper::toMovimientoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDto findById(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new MovimientoNotFoundException("No se encontro el movimiento con ID: " + id));
        return MovimientoMapper.toMovimientoDTO(movimiento);
    }

    @Override
    public List<MovimientoDto> findByCuentaNumero(String numero) {
        List<MovimientoDto> movimientosDTO = new ArrayList<>();
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
    public List<ReporteDto> findByCuentaNumeroAndFechaBetween(String numero, String fechaInicio, String fechaFin) {
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);
        List<ReporteDto> reporteDtos = new ArrayList<>();
        List<Movimiento> movimientos = movimientoRepository.findByCuentaNumeroAndFechaBetween(numero, inicioFecha, finFecha);
        if(!movimientos.isEmpty()){
            for (Movimiento movimiento : movimientos) {
                reporteDtos.add(MovimientoMapper.toReporteDTO(movimiento));
            }
        }else {
            throw new MovimientoNotFoundException("No se encontro el movimientos con el numero de cuenta : "
                    + numero +" en el rango de fechas: " + fechaInicio + " - " + fechaFin );
        }
        return reporteDtos;
    }

    public List<ReporteDto> findByNombreAndFechaBetween(String nombre, String fechaInicio, String fechaFin) {
        Date inicioFecha = Conversion.convertStringToDate(fechaInicio);
        Date finFecha = Conversion.convertStringToDate(fechaFin);
        LocalDate localDateFin = finFecha.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDateSiguiente = localDateFin.plusDays(1);
        Date fechaSiguiente = Date.from(localDateSiguiente.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<ReporteDto> reporteDtos = new ArrayList<>();
        Cliente cliente = clienteRepository.findByNombre(nombre);
        if(cliente != null){
            List<Cuenta> cuentas = cuentaRepository.findByClienteId(cliente.getId());
            if(!cuentas.isEmpty()){
                for (Cuenta cuenta : cuentas) {
                    List<Movimiento> movimientos = movimientoRepository.findByCuentaNumeroAndFechaBetween(cuenta.getNumero(), inicioFecha, fechaSiguiente);
                    if(!movimientos.isEmpty()){
                        for (Movimiento movimiento : movimientos) {
                            Cliente cliente1 = clienteRepository.findById(movimiento.getCuenta().getCliente().getId()).get();
                            ReporteDto reporteDTO = MovimientoMapper.toReporteDTO(movimiento);
                            reporteDTO.setCliente(cliente1.getNombre());
                            if("Retiro".equals(movimiento.getTipo())){
                                reporteDTO.setMovimiento("-"+movimiento.getValor());
                            }
                            reporteDtos.add(reporteDTO);
                        }
                    }
                }
            }
        }

        return reporteDtos;
    }

    public double retirar(String valor, double saldo) {
        valor = coverterNegativotoPositivo(valor);
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

    public String coverterNegativotoPositivo(String valor){
        if (valor.contains("-")){
            valor = valor.replace("-","");
        }
        return valor;
    }

}
