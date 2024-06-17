package com.ipartek.springboot.backend.elpisito.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipartek.springboot.backend.elpisito.models.entity.Usuario;
import com.ipartek.springboot.backend.elpisito.models.services.IUsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	private final String VAR = "User";

	@GetMapping("/usuarios-activos")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Usuario> users = new ArrayList<>();
		try {
			users = usuarioService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (users.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
	}

	@GetMapping("/usuarios")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Usuario> users = new ArrayList<>();
		try {
			users = usuarioService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (users.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Usuario>>(users, HttpStatus.OK);
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Usuario user = null;
		try {
			user = usuarioService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (user == null) {
			response.put("Message", "Data Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Has Not Found On Id -> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@PostMapping("/usuario")
	public ResponseEntity<?> create(@RequestBody Usuario u) {
		Map<String, Object> response = new HashMap<>();
		Usuario user = null;
		try {
			user = usuarioService.save(u);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + user.getUser() + " Succesfully Created");
		response.put(VAR, user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PutMapping("/usuario")
	public ResponseEntity<?> update(@RequestBody Usuario u) {
		Map<String, Object> response = new HashMap<>();
		Long id = u.getId();
		Usuario user = usuarioService.findById(id);
		Usuario userUpdated = null;
		try {
			userUpdated = usuarioService.save(u);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + user.getId() + " Succesfully Updated");
		response.put(VAR, userUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Usuario user = usuarioService.findById(id);
		try {
			usuarioService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + user.getId() + " Succesfully Deleted");
		response.put(VAR, user);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
