package com.matrix.tech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.VideoJuego;

public interface IVideojuegoDao extends CrudRepository<VideoJuego, Long>{
	
	@Query("select v from VideoJuego v where v.titulo like %?1% and v.stock > 0")
	public List<VideoJuego> findByTitulo(String term);
	
	@Query("select v from VideoJuego v where v.director like %?1% and v.protagonistas like %?2% and v.productor like %?3%")
	public List<VideoJuego> finByCustomize(String director, String protag, String productor);
}
