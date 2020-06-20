package com.matrix.tech.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.matrix.tech.models.Tecnologia;

public interface ITecnologiaService {

	public List<Tecnologia> finAll();

	public Tecnologia findById(Long id);

	public Tecnologia save(Tecnologia tecnologia);

	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public Tecnologia Actualizar(Tecnologia tecnologiaActual, Tecnologia Actualizado);
}
