package com.example.customers.service;

import com.example.customers.dto.ClienteDto;
import com.example.customers.dto.LoginRequest;
import com.example.customers.entity.Cliente;
import com.example.customers.exception.ClienteYaExisteException;
import com.example.customers.jwt.JwtService;
import com.example.customers.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthServiceImpl{

    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public ClienteDto.AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentificacion(), request.getContrasena()));
        UserDetails user= clienteRepository.findByIdentificacion(request.getIdentificacion())
                .orElseThrow(() -> new UsernameNotFoundException("User not fournd"));;
        String token=jwtService.getToken(user);
        return ClienteDto
                .AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public ClienteDto.AuthResponse register(Cliente cliente) {
        Optional<Cliente> clientRequest = clienteRepository.findByIdentificacion(cliente.getIdentificacion());
        if (!clientRequest.isEmpty()) {
            throw new ClienteYaExisteException("La identificación debe ser única");
        } else {
            cliente.setContrasena(passwordEncoder.encode(cliente.getContrasena()));
            clienteRepository.save(cliente);
        }
        return ClienteDto
                .AuthResponse.builder()
                .token(jwtService.getToken(cliente))
                .build();
    }
}
