package com.matrix.tech.dao;

import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
