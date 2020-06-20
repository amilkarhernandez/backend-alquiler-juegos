package com.matrix.tech.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "alquiler_items")
public class ItemAlquiler implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer cantidad;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private VideoJuego videojuego;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public VideoJuego getVideojuego() {
		return videojuego;
	}

	public void setVideojuego(VideoJuego videojuego) {
		this.videojuego = videojuego;
	}
	
	public Double getSubtotal() {
		return this.cantidad.doubleValue() * videojuego.getValorUnitario();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
