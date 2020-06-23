package com.matrix.tech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

	@Query("SELECT a FROM Cliente a WHERE a.nit = ?1")
	public Cliente findByNit(String nit);
	
	@Query(value = "SELECT B.ID, B.NIT, B.NOMBRES, B.APELLIDOS, B.TELEFONO, B.EDAD, B.FECHA_NAC, B.GENERO, COUNT(A.ID) AS TOTAL FROM alquileres  A INNER JOIN cliente B ON B.ID=A.CLIENTE_ID INNER JOIN alquiler_items C ON C.ALQUILER_ID = A.ID INNER JOIN video_juego D ON D.ID= C.VIDEOJUEGO_ID GROUP BY NIT, APELLIDOS,  NOMBRES, TELEFONO, EDAD, FECHA_NAC, GENERO, B.id ORDER BY TOTAL DESC", nativeQuery = true)
	public List<Cliente> findTopCliente();
}
