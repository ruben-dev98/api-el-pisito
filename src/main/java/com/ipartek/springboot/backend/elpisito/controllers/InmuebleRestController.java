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

import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;
import com.ipartek.springboot.backend.elpisito.models.services.IInmuebleService;

@RestController
@RequestMapping("/api")
public class InmuebleRestController {

	@Autowired
	private IInmuebleService inmuebleService;
	private final String VAR = "Inmueble";

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/inmuebles-activos")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Inmueble> inmuebles = new ArrayList<>();
		try {
			inmuebles = inmuebleService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inmuebles.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Inmueble>>(inmuebles, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/inmuebles/{pob}/{tipo}/{op}")
	public ResponseEntity<?> findAllActivesFinder(@PathVariable Long pob, @PathVariable Long tipo,
			@PathVariable String op) {
		Map<String, Object> response = new HashMap<>();
		List<Inmueble> inmuebles = new ArrayList<>();
		try {
			// inmuebles = inmuebleService.findAllFinder(pob, tipo, op);
			inmuebles = inmuebleService.findByPoblacionAndTipoAndOperacionAndActivoTrue(pob, tipo, op);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Finder " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		/*
		 * if(inmuebles.isEmpty()) { response.put("Message",
		 * "Data Has Not Found On Finder " + VAR); response.put("Error",
		 * "Data Not Found"); return new ResponseEntity<Map<String, Object>>(response,
		 * HttpStatus.NOT_FOUND); }
		 */
		return new ResponseEntity<List<Inmueble>>(inmuebles, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/inmuebles")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Inmueble> inmuebles = new ArrayList<>();
		try {
			inmuebles = inmuebleService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inmuebles.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Inmueble>>(inmuebles, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/inmuebles-portada")
	public ResponseEntity<?> findAllPortada() {
		Map<String, Object> response = new HashMap<>();
		List<Inmueble> inmuebles = new ArrayList<>();
		try {
			inmuebles = inmuebleService.findAllPortada();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Portada " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inmuebles.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Portada " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Inmueble>>(inmuebles, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/inmueble/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Inmueble inmueble = null;
		try {
			inmueble = inmuebleService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (inmueble == null) {
			response.put("Message", "Data Has Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Not Found On Id -> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Inmueble>(inmueble, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/inmueble")
	public ResponseEntity<?> create(@RequestBody Inmueble i) {
		Map<String, Object> response = new HashMap<>();
		Inmueble inmueble = null;
		try {
			inmueble = inmuebleService.save(i);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + inmueble.getId() + " Succesfully Created");
		response.put(VAR, inmueble);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PutMapping("/inmueble")
	public ResponseEntity<?> update(@RequestBody Inmueble i) {
		Map<String, Object> response = new HashMap<>();
		Long id = i.getId();
		Inmueble inmueble = inmuebleService.findById(id);
		Inmueble inmuebleUpdated = null;
		try {
			inmuebleUpdated = inmuebleService.save(i);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + inmueble.getId() + " Succesfully Updated");
		response.put(VAR, inmuebleUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/inmueble/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Inmueble inmueble = inmuebleService.findById(id);
		try {
			inmuebleService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + inmueble.getId() + " Succesfully Deleted");
		response.put(VAR, inmueble);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
