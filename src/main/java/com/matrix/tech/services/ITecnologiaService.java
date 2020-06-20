package com.matrix.tech.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.matrix.tech.models.TecnologiaCat;


public interface ITecnologiaService {

	public List<TecnologiaCat> finAll();

	public TecnologiaCat findById(Long id);

	public TecnologiaCat save(TecnologiaCat tecnologia);

	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public TecnologiaCat Actualizar(TecnologiaCat tecnologiaActual, TecnologiaCat Actualizado);
}
