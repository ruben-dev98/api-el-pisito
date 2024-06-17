package com.ipartek.springboot.backend.elpisito.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.elpisito.models.entity.Poblacion;
import com.ipartek.springboot.backend.elpisito.models.services.IPoblacionService;

@RestController
@RequestMapping("/api")
public class PoblacionRestController {

	@Autowired
	private IPoblacionService poblacionService;
	private final String VAR = "Poblacion";

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/poblaciones-activas")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Poblacion> pobs = new ArrayList<>();
		try {
			pobs = poblacionService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pobs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Poblacion>>(pobs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/poblaciones")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Poblacion> pobs = new ArrayList<>();
		try {
			pobs = poblacionService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pobs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Poblacion>>(pobs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/poblacion/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Poblacion pob = null;
		try {
			pob = poblacionService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (pob == null) {
			response.put("Message", "Data Has Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Not Found On Id -> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Poblacion>(pob, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/poblacion")
	public ResponseEntity<?> create(@RequestBody Poblacion p) {
		Map<String, Object> response = new HashMap<>();
		Poblacion pob = null;
		try {
			pob = poblacionService.save(p);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + pob.getNombre() + " Succesfully Created");
		response.put(VAR, pob);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PutMapping("/poblacion")
	public ResponseEntity<?> update(@RequestBody Poblacion p) {
		Map<String, Object> response = new HashMap<>();
		Long id = p.getId();
		Poblacion pob = poblacionService.findById(id);
		Poblacion pobUpdated = null;
		try {
			pobUpdated = poblacionService.save(p);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + pob.getId() + " Succesfully Updated");
		response.put(VAR, pobUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/poblacion/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Poblacion pob = poblacionService.findById(id);
		try {
			poblacionService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + pob.getId() + " Succesfully Deleted");
		response.put(VAR, pob);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
