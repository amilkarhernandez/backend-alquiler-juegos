package com.matrix.tech.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.matrix.tech.dao.ITecnologiaDao;
import com.matrix.tech.models.TecnologiaCat;

@Service
public class TecnologiaServiceImp implements ITecnologiaService{

	@Autowired
	private ITecnologiaDao tecnologiaDao;
	
	@Override
	public List<TecnologiaCat> finAll() {
		return (List<TecnologiaCat>) tecnologiaDao.findAll();
	}

	@Override
	public TecnologiaCat findById(Long id) {
		return tecnologiaDao.findById(id).orElse(null);
	}

	@Override
	public TecnologiaCat save(TecnologiaCat tecnologia) {
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
	public TecnologiaCat Actualizar(TecnologiaCat tecnologiaActual, TecnologiaCat Actualizado) {
		tecnologiaActual.setDescripcion(Actualizado.getDescripcion());
		
		return tecnologiaActual;
	}

}
