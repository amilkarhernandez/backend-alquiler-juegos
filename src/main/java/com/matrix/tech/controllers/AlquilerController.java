package com.matrix.tech.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matrix.tech.models.Alquiler;
import com.matrix.tech.models.ItemAlquiler;
import com.matrix.tech.models.VideoJuego;
import com.matrix.tech.services.IAlquilerService;
import com.matrix.tech.services.IVideojuegoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AlquilerController {

	@Autowired
	private IAlquilerService alquilerService;

	@Autowired
	private IVideojuegoService videojuegoService;

	@GetMapping("/alquiler/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Alquiler show(@PathVariable Long id) {
		return alquilerService.findAlquilerById(id);
	}

	@DeleteMapping("/alquiler/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		alquilerService.deleteAlquilerById(id);
	}

	@GetMapping("/alquiler/filtrarvideojuegos/{term}")
	@ResponseStatus(HttpStatus.OK)
	public List<VideoJuego> filtrarVideojuegos(@PathVariable String term) {
		return alquilerService.findByTitulo(term);
	}
	
	@GetMapping("/alquiler/consulta/{nit}")
	@ResponseStatus(HttpStatus.OK)
	public List<Alquiler> buscarAlquilerNit(@PathVariable String nit) {
		return (List<Alquiler>) alquilerService.findByNit(nit);
	}
	
	@GetMapping("/alquiler/ventadia/{fecha}")
	@ResponseStatus(HttpStatus.OK)
	public List<Alquiler> buscarAlquilerFechaventa(@PathVariable String fecha) {
		return (List<Alquiler>) alquilerService.findByFechaVenta(fecha);
	}

	@PostMapping("/alquiler")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> crear(@RequestBody Alquiler alquiler) {
		Alquiler alq = alquiler;
		Map<String, Object> response = new HashMap<>();

		for (ItemAlquiler item : alq.getItems()) {
			VideoJuego videoMod = videojuegoService.findById(item.getVideojuego().getId());
			if (videoMod.getStock() == 0) {
				response.put("mensaje",
						"Error: El Video Juego ".concat(videoMod.getTitulo()).concat(" Tiene un Stock en Cero."));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			if (item.getCantidad() > videoMod.getStock()) {
				response.put("mensaje", "Error: El Video Juego ".concat(videoMod.getTitulo())
						.concat(" No Cuenta con la Cantidad Ingresada"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

			videoMod.setStock(alquilerService.restarStock(videoMod.getStock(), item.getCantidad()));

			// guardamos el Video Juego y El Alquiler
			videojuegoService.save(videoMod);

		}
		alquilerService.saveAlquiler(alquiler);

		response.put("mensaje", "El Alquiler ha sido creado con Exito");
		response.put("Alquiler", alq);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/alquiler/list")
	public List<Alquiler> index() {
		return alquilerService.findAllByEstado();
	}
	
	@GetMapping("/alquiler/devolver/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> cambiarEstado(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Alquiler alquiler = alquilerService.findAlquilerById(id);
		for (ItemAlquiler item : alquiler.getItems()) {
			VideoJuego videoMod = videojuegoService.findById(item.getVideojuego().getId());
			videoMod.setStock(videoMod.getStock() + item.getCantidad());
			videojuegoService.save(videoMod);
		}
		alquiler.setEstado("RECIBIDO");
		alquilerService.saveAlquiler(alquiler);
		
		response.put("mensaje", "El Alquiler ha sido Devuelto con Exito");
		response.put("Alquiler", alquiler);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}
