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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.matrix.tech.models.TecnologiaCat;
import com.matrix.tech.services.ITecnologiaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class TecnologiaController {
	
	@Autowired
	private ITecnologiaService tecnologiaService;
	
	@GetMapping("/tecnologias")
	public List<TecnologiaCat> index() {
		return tecnologiaService.finAll();
	}
	
	@GetMapping("/tecnologias/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		TecnologiaCat tecnologia = null;
		Map<String, Object> response = new HashMap<>();

		try {
			tecnologia = tecnologiaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Realizar la Consulta en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tecnologia == null) {
			response.put("mensaje",
					"La Tecnologia con el Id:".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<TecnologiaCat>(tecnologia, HttpStatus.OK);
	}
	
	@PostMapping("/tecnologias")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@Valid @RequestBody TecnologiaCat tecnologia, BindingResult result) {
		TecnologiaCat tecnologiaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = tecnologiaService.validacion(result);

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			tecnologiaNew = tecnologiaService.save(tecnologia);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Realizar el Insert en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La tecnologia ha sido creada con Exito");
		response.put("tecnologia", tecnologiaNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/tecnologias/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody TecnologiaCat tecnologia, BindingResult result,
			@PathVariable Long id) {

		TecnologiaCat tecnologiaActual = tecnologiaService.findById(id);
		TecnologiaCat tecnologiaUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = tecnologiaService.validacion(result);

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (tecnologiaActual == null) {
			response.put("mensaje", "Error: no se puede Editar, La Tecnologia con el Id:".concat(id.toString())
					.concat(" no existe en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			tecnologiaUpdated = tecnologiaService.save(tecnologiaService.Actualizar(tecnologiaActual, tecnologia));
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Actualizar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La Tecnologia ha sido Actualizado con Exito");
		response.put("tecnologia", tecnologiaUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// ELIMINAR UN REGISTRO
	@DeleteMapping("/tecnologias/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			tecnologiaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al Eliminar en la Base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La Tecnologia ha sido Eliminada con Exito");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

}
