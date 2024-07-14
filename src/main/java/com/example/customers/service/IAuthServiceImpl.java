package com.example.customers.service;


import com.example.customers.dto.ClienteDto;
import com.example.customers.dto.LoginRequest;
import com.example.customers.entity.Cliente;

public interface IAuthServiceImpl {
    ClienteDto.AuthResponse login(LoginRequest loginRequest);
    ClienteDto.AuthResponse register(Cliente cliente);
}
