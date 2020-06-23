package com.matrix.tech.dao;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Rangos;
import com.matrix.tech.models.VideoJuego;

public interface IVideojuegoDao extends CrudRepository<VideoJuego, Long>{
	
	@Query("select v from VideoJuego v where v.titulo like %?1% and v.stock > 0")
	public List<VideoJuego> findByTitulo(String term);
	
	@Query("select v from VideoJuego v where v.director like %?1% or v.protagonistas like %?2% or v.productor like %?3%")
	public List<VideoJuego> finByCustomize(String director, String protag, String productor);
	
	@Query(value = "SELECT A.ID, CASE WHEN B.EDAD BETWEEN 0 AND 10 THEN D.TITULO ELSE '' END AS diez, CASE WHEN B.EDAD BETWEEN 21 AND 30 THEN D.TITULO ELSE '' END AS veinte, CASE WHEN B.EDAD BETWEEN 31 AND 40 THEN D.TITULO ELSE '' END AS treinta, CASE WHEN B.EDAD BETWEEN 41 AND 50 THEN D.TITULO ELSE '' END AS cuarenta, CASE WHEN B.EDAD BETWEEN 51 AND 60 THEN D.TITULO ELSE '' END AS cincuenta, CASE WHEN B.EDAD BETWEEN 61 AND 70 THEN D.TITULO ELSE '' END AS sesenta, CASE WHEN B.EDAD BETWEEN 71 AND 80 THEN D.TITULO ELSE '' END AS setenta FROM ALQUILERES  A INNER JOIN CLIENTE B ON B.ID=A.CLIENTE_ID INNER JOIN ALQUILER_ITEMS C ON C.ALQUILER_ID = A.ID INNER JOIN VIDEO_JUEGO D ON D.ID= C.VIDEOJUEGO_ID GROUP BY ANO, DIRECTOR, NOMBRE, PRODUCTOR, PROTAGONISTAS, STOCK, TITULO, VALOR_UNITARIO, TECNOLOGIA_ID, EDAD, A.ID", nativeQuery = true)
	public List<Object[]> findRangosJuegos();
}
