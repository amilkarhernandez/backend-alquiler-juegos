package com.matrix.tech.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.matrix.tech.models.Cliente;

public interface IClienteService {
	
	public List<Cliente> finAll();

	public Cliente findById(Long id);

	public Cliente save(Cliente cliente);

	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public Cliente Actualizar(Cliente clienteActual, Cliente Actualizado);
	
	public Cliente findByNit(String nit);

}
