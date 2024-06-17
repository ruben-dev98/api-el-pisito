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

import com.ipartek.springboot.backend.elpisito.models.entity.Tipo;
import com.ipartek.springboot.backend.elpisito.models.services.ITipoService;

@RestController
@RequestMapping("/api")
public class TipoRestController {

	@Autowired
	private ITipoService tipoService;
	private final String VAR = "Tipo";

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/tipos-activos")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Tipo> tipos = new ArrayList<>();
		try {
			tipos = tipoService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tipos.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Tipo>>(tipos, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/tipos")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Tipo> tipos = new ArrayList<>();
		try {
			tipos = tipoService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tipos.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Tipo>>(tipos, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/tipo/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Tipo tipo = null;
		try {
			tipo = tipoService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (tipo == null) {
			response.put("Message", "Data Has Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Not Found On Id-> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tipo>(tipo, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/tipo")
	public ResponseEntity<?> create(@RequestBody Tipo t) {
		Map<String, Object> response = new HashMap<>();
		Tipo tipo = null;
		try {
			tipo = tipoService.save(t);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + tipo.getNombre() + " Succesfully Created");
		response.put(VAR, tipo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PutMapping("/tipo")
	public ResponseEntity<?> update(@RequestBody Tipo t) {
		Map<String, Object> response = new HashMap<>();
		Long id = t.getId();
		Tipo tipo = tipoService.findById(id);
		Tipo tipoUpdated = null;
		try {
			tipoUpdated = tipoService.save(t);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + tipo.getId() + " Succesfully Updated");
		response.put(VAR, tipoUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/tipo/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Tipo tipo = tipoService.findById(id);
		try {
			tipoService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + tipo.getId() + " Succesfully Deleted");
		response.put(VAR, tipo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
