package com.matrix.tech.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.matrix.tech.dao.IClienteDao;
import com.matrix.tech.models.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	public List<Cliente> finAll() {
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	public List<String> validacion(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public Cliente Actualizar(Cliente clienteActual, Cliente Actualizado) {
		clienteActual.setNombres(Actualizado.getNombres());
		clienteActual.setApellidos(Actualizado.getApellidos());
		clienteActual.setTelefono(Actualizado.getTelefono());
		clienteActual.setGenero(Actualizado.getGenero());
		clienteActual.setFecha_nac(Actualizado.getFecha_nac());
		
		return clienteActual;
	}

}
