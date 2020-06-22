package com.matrix.tech.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matrix.tech.models.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

	@Query("SELECT a FROM Cliente a WHERE a.nit = ?1")
	public Cliente findByNit(String nit);
	
	@Query(value = "SELECT B.ID, B.NIT, B.NOMBRES, B.APELLIDOS, B.TELEFONO, B.EDAD, B.FECHA_NAC, B.GENERO, COUNT(A.ID) AS TOTAL FROM ALQUILERES  A INNER JOIN CLIENTE B ON B.ID=A.CLIENTE_ID INNER JOIN ALQUILER_ITEMS C ON C.ALQUILER_ID = A.ID INNER JOIN VIDEO_JUEGO D ON D.ID= C.VIDEOJUEGO_ID GROUP BY NIT, APELLIDOS,  NOMBRES, TELEFONO, EDAD, FECHA_NAC, GENERO ORDER BY TOTAL DESC", nativeQuery = true)
	public List<Cliente> findTopCliente();
}
