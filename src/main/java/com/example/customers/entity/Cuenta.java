package com.example.customers.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name= "cuentas")
@Table(name = "cuentas", uniqueConstraints = {@UniqueConstraint(columnNames = {"numero"})})
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuentaId")
    private Long cuentaId;

    @Column(name = "numero", nullable = false)
    private String numero;

    @Column(name = "tipo", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldoInicial", nullable = false)
    private double saldoInicial;

    @Column(name = "estado", columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "limiteDiario", nullable = false, columnDefinition = "DOUBLE DEFAULT 1000.0")
    private double limiteDiario;

    @ManyToOne
    @JoinColumn(name = "clienteId", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    List<Movimiento> movimientos;
}
