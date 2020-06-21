package com.matrix.tech.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

	@Query("SELECT a FROM Cliente a WHERE a.nit = ?1")
	public Cliente findByNit(String nit);
}
