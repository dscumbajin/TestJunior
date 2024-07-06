package com.example.customers.repository;

import com.example.customers.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Serializable> {
}
