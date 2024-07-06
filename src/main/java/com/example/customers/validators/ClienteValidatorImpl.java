package com.example.customers.validators;

import com.example.customers.entity.Cliente;
import com.example.customers.exception.ApiUnprocessableEntity;
import com.example.customers.exception.ClienteYaExisteException;
import com.example.customers.repository.ClienteRepository;
import com.example.customers.service.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteValidatorImpl implements IClienteValidator{

    @Autowired
    private ClienteRepository clienteService;

    @Override
    public void validador(Cliente cliente) throws ApiUnprocessableEntity {
        if(cliente.getIdentificacion() == null || cliente.getIdentificacion().isEmpty()){
            throw new ApiUnprocessableEntity("La identificacion es requerida");
        }
        if(cliente.getIdentificacion().length() != 10){
            throw new ApiUnprocessableEntity("La identificacion debe tener 10 caracteres");
        }

        if(!cliente.getIdentificacion().isEmpty() ){
            Cliente buscado = clienteService.findByIdentificacion(cliente.getIdentificacion());
            if (buscado != null ){
                throw new ApiUnprocessableEntity("La identificacion debe ser unica");
            }
        }
        if(cliente.getNombre() == null || cliente.getNombre().isEmpty()){
            throw new ApiUnprocessableEntity("La nombre es requerido");
        }
        if(cliente.getDireccion() == null || cliente.getDireccion().isEmpty()){
            throw new ApiUnprocessableEntity("La direccion es requerida");
        }
        if(cliente.getTelefono() == null || cliente.getTelefono().isEmpty()){
            throw new ApiUnprocessableEntity("El telefono es requerido");
        }
        if(cliente.getContrasena() == null || cliente.getContrasena().isEmpty()){
            throw new ApiUnprocessableEntity("La contraseña es requerida");
        }
    }
}
