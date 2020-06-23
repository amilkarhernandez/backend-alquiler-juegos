package com.matrix.tech.services;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.matrix.tech.models.Rangos;
import com.matrix.tech.models.VideoJuego;


public interface IVideojuegoService {

	public List<VideoJuego> finAll();

	public VideoJuego findById(Long id);

	public VideoJuego save(VideoJuego videojuego);

	public void delete(Long id);
	
	public List<String> validacion(BindingResult result);
	
	public VideoJuego Actualizar(VideoJuego videojuegoActual, VideoJuego Actualizado);
	
	public Long count();
	
	//Consulta
	public List<VideoJuego> finByCustomize(String director, String protag, String productor);
	
	//public List<Rangos> findRangosJuegos();
}
