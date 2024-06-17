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

import com.ipartek.springboot.backend.elpisito.models.entity.Imagen;
import com.ipartek.springboot.backend.elpisito.models.services.IImagenService;

@RestController
@RequestMapping("/api")
public class ImagenRestController {

	@Autowired
	private IImagenService imagenService;
	private final String VAR = "Image";

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/imagenes-activas")
	public ResponseEntity<?> findAllActives() {
		Map<String, Object> response = new HashMap<>();
		List<Imagen> imgs = new ArrayList<>();
		try {
			imgs = imagenService.findAllActive();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find Activo " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (imgs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find Activo " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Imagen>>(imgs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/imagenes")
	public ResponseEntity<?> findAll() {
		Map<String, Object> response = new HashMap<>();
		List<Imagen> imgs = new ArrayList<>();
		try {
			imgs = imagenService.findAll();
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (imgs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Imagen>>(imgs, HttpStatus.OK);
	}

	////@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/imagenes-activas/{id}")
	public ResponseEntity<?> findAllActiveInmueble(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		List<Imagen> imgs = new ArrayList<>();
		try {
			imgs = imagenService.findAllActiveInmueble(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (imgs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Imagen>>(imgs, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/imagenes/{id}")
	public ResponseEntity<?> findAllInmueble(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		List<Imagen> imgs = new ArrayList<>();
		try {
			imgs = imagenService.findAllInmueble(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find All " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (imgs.isEmpty()) {
			response.put("Message", "Data Has Not Found On Find All " + VAR);
			response.put("Error", "Data Not Found");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Imagen>>(imgs, HttpStatus.OK);
	}

	@GetMapping("/imagen/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Imagen img = null;
		try {
			img = imagenService.findById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Find " + VAR + " By Id");
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (img == null) {
			response.put("Message", "Data Has Not Found On Find " + VAR + " By Id");
			response.put("Error", "Data Not Found On Id -> " + id);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Imagen>(img, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/imagen")
	public ResponseEntity<?> create(@RequestBody Imagen i) {
		Map<String, Object> response = new HashMap<>();
		Imagen img = null;
		try {
			img = imagenService.save(i);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Create " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + img.getNombre() + " Succesfully Created");
		response.put(VAR, img);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PutMapping("/imagen")
	public ResponseEntity<?> update(@RequestBody Imagen i) {
		Map<String, Object> response = new HashMap<>();
		Long id = i.getId();
		Imagen img = imagenService.findById(id);
		Imagen imgUpdated = null;
		try {
			imgUpdated = imagenService.save(i);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Update " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + img.getId() + " Succesfully Updated");
		response.put(VAR, imgUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/imagen/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Imagen img = imagenService.findById(id);
		try {
			imagenService.deleteById(id);
		} catch (DataAccessException ex) {
			response.put("Message", "Data Error On DB, Error On Delete " + VAR);
			response.put("Error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("Message", VAR + " " + img.getId() + " Succesfully Deleted");
		response.put(VAR, img);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
