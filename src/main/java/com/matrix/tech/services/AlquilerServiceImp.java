package com.matrix.tech.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matrix.tech.dao.IAlquilerDao;
import com.matrix.tech.dao.IVideojuegoDao;
import com.matrix.tech.models.Alquiler;
import com.matrix.tech.models.VideoJuego;

@Service
public class AlquilerServiceImp implements IAlquilerService{

	@Autowired
	private IAlquilerDao alquilerDao;
	
	@Autowired
	private IVideojuegoDao videojuegoDao;
	
	@Override
	public Alquiler findAlquilerById(Long id) {
		return alquilerDao.findById(id).orElse(null);
	}

	@Override
	public Alquiler saveAlquiler(Alquiler alquiler) {
		return alquilerDao.save(alquiler);
	}

	@Override
	public void deleteAlquilerById(Long id) {
		alquilerDao.deleteById(id);
	}

	@Override
	public List<VideoJuego> findByTitulo(String term) {
		return videojuegoDao.findByTitulo(term);
	}

	@Override
	public int restarStock(int stock, int cantidad) {
		return stock-cantidad;
	}

}
