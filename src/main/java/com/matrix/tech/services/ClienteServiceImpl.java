package com.matrix.tech.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
		SimpleDateFormat formato = new SimpleDateFormat("yyyy", Locale.US);
		Date fechaActual = new Date();
		Long edad = Long.valueOf(formato.format(fechaActual)) - Long.valueOf(formato.format(cliente.getFecha_nac()));
		cliente.setEdad(edad);
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
		clienteActual.setNit(Actualizado.getNit());
		clienteActual.setNombres(Actualizado.getNombres());
		clienteActual.setApellidos(Actualizado.getApellidos());
		clienteActual.setTelefono(Actualizado.getTelefono());
		clienteActual.setGenero(Actualizado.getGenero());
		clienteActual.setFecha_nac(Actualizado.getFecha_nac());
		
		return clienteActual;
	}

	@Override
	public Cliente findByNit(String nit) {
		return clienteDao.findByNit(nit);
	}

	@Override
	public Long count() {
		return clienteDao.count();
	}

	@Override
	public List<Cliente> findTopCliente() {
		return clienteDao.findTopCliente();
	}

}
