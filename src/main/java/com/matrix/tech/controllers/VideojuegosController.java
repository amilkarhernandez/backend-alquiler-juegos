package com.matrix.tech.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matrix.tech.models.Rangos;
import com.matrix.tech.models.VideoJuego;
import com.matrix.tech.services.IVideojuegoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class VideojuegosController {

	@Autowired
	private IVideojuegoService videojuegoService;
	
	@GetMapping("/videojuegos")
	public List<VideoJuego> index() {
		return videojuegoService.finAll();
	}
	
	@GetMapping("/videojuegos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		VideoJuego videojuego = null;
		Map<String, Object> response = new HashMap<>();

		try {
			videojuego = videojuegoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (videojuego == null) {
			response.put("mensaje",
					"El Video Juego con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<VideoJuego>(videojuego, HttpStatus.OK);
	}
	
	@PostMapping("/videojuegos")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody VideoJuego videojuego, BindingResult result) {
		VideoJuego videojuegoNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = videojuegoService.validacion(result);

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			videojuegoNew = videojuegoService.save(videojuego);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Video Juego ha sido creado con Exito");
		response.put("videojuego", videojuegoNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/videojuegos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody VideoJuego videojuego, BindingResult result,
			@PathVariable Long id) {

		VideoJuego videojuegoActual = videojuegoService.findById(id);
		VideoJuego videojuegoUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = videojuegoService.validacion(result);

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (videojuegoActual == null) {
			response.put("mensaje", "Error: no se puede Editar, El Video Juego con el Id:".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			videojuegoUpdated = videojuegoService.save(videojuegoService.Actualizar(videojuegoActual, videojuego));
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Actualizar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Video Juego ha sido Actualizado con Exito");
		response.put("videojuego", videojuegoUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// ELIMINAR UN REGISTRO
	@DeleteMapping("/videojuegos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			videojuegoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Eliminar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Video Juego ha sido Eliminado con Exito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
	
	@GetMapping("/videojuegos/total")
	public Long total() {
		return videojuegoService.count();
	}
	
	@GetMapping("/videojuegos/busqueda")
	public List<VideoJuego> busqueda(@RequestParam(value = "director") String director, 
			@RequestParam(value = "protag") String protag, 
			@RequestParam(value = "productor") String productor){
		return videojuegoService.finByCustomize(director, protag, productor);
	}
	
	//@GetMapping("/videojuegos/rangos")
	//public List<Rangos> rangos() {
	//	return videojuegoService.findRangosJuegos();
	//}
	
}
