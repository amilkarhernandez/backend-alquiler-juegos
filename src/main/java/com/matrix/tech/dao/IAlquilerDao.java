package com.matrix.tech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Alquiler;

public interface IAlquilerDao extends CrudRepository<Alquiler, Long>{

	@Query("SELECT a FROM Alquiler a JOIN a.cliente c WHERE c.nit = ?1")
	public List<Alquiler> findByNit(String nit);
}
