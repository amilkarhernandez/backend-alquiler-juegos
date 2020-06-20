package com.matrix.tech.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.matrix.tech.dao.ITecnologiaDao;
import com.matrix.tech.models.Tecnologia;

@Service
public class TecnologiaServiceImp implements ITecnologiaService{

	@Autowired
	private ITecnologiaDao tecnologiaDao;
	
	@Override
	public List<Tecnologia> finAll() {
		return (List<Tecnologia>)tecnologiaDao.findAll();
	}

	@Override
	public Tecnologia findById(Long id) {
		return tecnologiaDao.findById(id).orElse(null);
	}

	@Override
	public Tecnologia save(Tecnologia tecnologia) {
		return tecnologiaDao.save(tecnologia);
	}

	@Override
	public void delete(Long id) {
		tecnologiaDao.deleteById(id);
	}

	@Override
	public List<String> validacion(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public Tecnologia Actualizar(Tecnologia tecnologiaActual, Tecnologia Actualizado) {
		tecnologiaActual.setDescripcion(Actualizado.getDescripcion());
		
		return tecnologiaActual;
	}

}
