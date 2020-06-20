package com.matrix.tech.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "No puede Ser vacio")
	@Column(length = 11)
	private String nit;

	@NotNull(message = "No puede Ser vacio")
	@Column(length = 200)
	private String nombres;

	@NotNull(message = "No puede Ser vacio")
	@Column(length = 200)
	private String apellidos;

	@NotNull(message = "No puede Ser vacio")
	@Column(length = 10)
	private String telefono;

	private String genero;

	@NotNull(message = "No puede Ser vacio")
	@Temporal(TemporalType.DATE)
	private Date fecha_nac;

	@JsonIgnoreProperties({ "cliente", "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente", cascade = CascadeType.ALL)
	private List<Alquiler> alquiler;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public List<Alquiler> getAlquiler() {
		return alquiler;
	}

	public void setAlquiler(List<Alquiler> alquiler) {
		this.alquiler = alquiler;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
