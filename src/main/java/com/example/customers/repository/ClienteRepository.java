package com.example.customers.repository;

import com.example.customers.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Serializable> {

    Optional<Cliente> findByIdentificacion(String identificacion);
    Cliente findByNombre(String nombre);
}
