package com.example.customers.repository;

import com.example.customers.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Serializable> {

    public Cuenta findByNumero(String numero);
}
