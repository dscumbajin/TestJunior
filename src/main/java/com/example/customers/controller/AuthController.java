package com.example.customers.controller;
import com.example.customers.dto.ClienteDto;
import com.example.customers.dto.LoginRequest;
import com.example.customers.entity.Cliente;
import com.example.customers.service.AuthService;
import com.example.customers.validators.ClienteValidatorImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    private final AuthService authService;
    private final ClienteValidatorImpl clienteValidator;

    @PostMapping(value = "login")
    public ResponseEntity<ClienteDto.AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @Transactional
    @PostMapping(value = "register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody Cliente request) {
        try {
            clienteValidator.validador(request);
            return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
