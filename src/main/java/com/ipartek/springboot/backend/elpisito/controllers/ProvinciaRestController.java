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

import com.ipartek.springboot.backend.elpisito.models.entity.Provincia;
import com.ipartek.springboot.backend.elpisito.models.services.IProvinciaService;

@RestController
@RequestMapping("/api")
public class ProvinciaRestController {

	@Autowired
	private IProvinciaService provinciaService;
	private final String VAR = "Provincia";

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/provincias-activas")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Provincia> provs = new ArrayList<>();
		try {
			provs = provinciaService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (provs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Provincia>>(provs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/provincias")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Provincia> provs = new ArrayList<>();
		try {
			provs = provinciaService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (provs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Provincia>>(provs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/provincia/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Provincia prov = null;
		try {
			prov = provinciaService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (prov == null) {
			response.put("Message", "Data Has Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Not Found On Id -> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Provincia>(prov, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/provincia")
	public ResponseEntity<?> create(@RequestBody Provincia p) {
		Map<String, Object> response = new HashMap<>();
		Provincia prov = null;
		try {
			prov = provinciaService.save(p);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + prov.getNombre() + " Succesfully Created");
		response.put(VAR, prov);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PutMapping("/provincia")
	public ResponseEntity<?> update(@RequestBody Provincia p) {
		Map<String, Object> response = new HashMap<>();
		Long id = p.getId();
		Provincia prov = provinciaService.findById(id);
		Provincia provUpdated = null;
		try {
			provUpdated = provinciaService.save(p);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + prov.getId() + " Succesfully Updated");
		response.put(VAR, provUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/provincia/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Provincia prov = provinciaService.findById(id);
		try {
			provinciaService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + prov.getId() + " Succesfully Deleted");
		response.put(VAR, prov);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
