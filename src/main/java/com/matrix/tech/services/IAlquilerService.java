package com.matrix.tech.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.matrix.tech.models.Alquiler;
import com.matrix.tech.models.VideoJuego;

public interface IAlquilerService {
	
	public Alquiler findAlquilerById(Long id);
	
	public Alquiler saveAlquiler(Alquiler alquiler);
	
	public void deleteAlquilerById(Long id);
	
	public List<VideoJuego> findByTitulo(String term);
	
	public int restarStock(int stock, int cantidad);
	
	public List<Alquiler> findByNit(String nit);
	
	public List<Alquiler> findByFechaVenta(String fecha);
	
	public List<Alquiler> findAllByEstado();

	public Long count();
	
	public ByteArrayInputStream exportAlquiler(List<Alquiler> alquiler) throws IOException;
}
