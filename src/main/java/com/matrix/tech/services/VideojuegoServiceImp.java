package com.matrix.tech.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.matrix.tech.dao.IVideojuegoDao;
import com.matrix.tech.models.VideoJuego;

@Service
public class VideojuegoServiceImp implements IVideojuegoService{

	@Autowired
	private IVideojuegoDao videojuegoDao;
	
	@Override
	public List<VideoJuego> finAll() {
		return (List<VideoJuego>)videojuegoDao.findAll();
	}

	@Override
	public VideoJuego findById(Long id) {
		return videojuegoDao.findById(id).orElse(null);
	}

	@Override
	public VideoJuego save(VideoJuego videojuego) {
		return videojuegoDao.save(videojuego);
	}

	@Override
	public void delete(Long id) {
		videojuegoDao.deleteById(id);
	}

	@Override
	public List<String> validacion(BindingResult result) {
		return result.getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
	}

	@Override
	public VideoJuego Actualizar(VideoJuego videojuegoActual, VideoJuego Actualizado) {
		
		videojuegoActual.setTitulo(Actualizado.getTitulo());
		videojuegoActual.setNombre(Actualizado.getNombre());
		videojuegoActual.setProductor(Actualizado.getProductor());
		videojuegoActual.setDirector(Actualizado.getDirector());
		videojuegoActual.setProtagonistas(Actualizado.getProtagonistas());
		videojuegoActual.setTecnologia(Actualizado.getTecnologia());
		videojuegoActual.setValorUnitario(Actualizado.getValorUnitario());
		videojuegoActual.setStock(Actualizado.getStock());
		return videojuegoActual;
	}

	@Override
	public Long count() {
		return videojuegoDao.count();
	}

	@Override
	public List<VideoJuego> finByCustomize(String director, String protag, String productor) {
		return videojuegoDao.finByCustomize(director, protag, productor);
	}

}
