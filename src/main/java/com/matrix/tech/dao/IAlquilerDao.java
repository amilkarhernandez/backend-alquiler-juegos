package com.matrix.tech.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.matrix.tech.models.Alquiler;

public interface IAlquilerDao extends CrudRepository<Alquiler, Long>{

	@Query("SELECT a FROM Alquiler a JOIN a.cliente c WHERE c.nit = ?1")
	public List<Alquiler> findByNit(String nit);
	
	@Query("SELECT a FROM Alquiler a WHERE a.fecha = :fechenv")
	public List<Alquiler> findByFecha(@Param("fechenv") Date fechenv);
	
}
