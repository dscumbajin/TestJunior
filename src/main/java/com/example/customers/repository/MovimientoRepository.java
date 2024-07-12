package com.example.customers.repository;

import com.example.customers.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Serializable> {
    public List<Movimiento> findByCuentaNumeroOrderByFechaDesc(String numero);
    public List<Movimiento> findByCuentaNumero(String numero);
    public List<Movimiento> findByCuentaNumeroAndFechaBetween(String numero, Date fechaInicio, Date fechaFin);
}
