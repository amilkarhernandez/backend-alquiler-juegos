package com.matrix.tech.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class VideoJuego implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 200)
	private String titulo;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 200)
	private String nombre;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 4)
	private Long ano;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 200)
	private String protagonistas;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 200)
	private String director;
	
	@NotNull(message = "no puede ser vacia")
	@Column(length = 500)
	private String productor;
	
	@NotNull(message = "no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tecnologia_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Tecnologia tecnologia;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getAno() {
		return ano;
	}
	public void setAno(Long ano) {
		this.ano = ano;
	}
	public String getProtagonistas() {
		return protagonistas;
	}
	public void setProtagonistas(String protagonistas) {
		this.protagonistas = protagonistas;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getProductor() {
		return productor;
	}
	public void setProductor(String productor) {
		this.productor = productor;
	}
	public Tecnologia getTecnologia() {
		return tecnologia;
	}
	public void setTecnologia(Tecnologia tecnologia) {
		this.tecnologia = tecnologia;
	}
	
	
	
	
}
