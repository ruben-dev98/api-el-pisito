package com.ipartek.springboot.backend.elpisito.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ipartek.springboot.backend.elpisito.storage.IStorageService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/media")
public class StorageRestController {

	@Autowired
	private IStorageService storageService;
	@Autowired
	private HttpServletRequest request;

	// Este metodo sirve para subir archivos de cualquier tipo y retorna un map
	// la url final del archivo subido para su posterior consulta
	// en el @RequestParam capturamos el archivo con el nombre "file".
	// es muy importante recordar que a la hora de enviar el archivo fisico
	// en la key de envio est mismo nombre
	//@CrossOrigin(origins = { "http://localhost:4200" })
	@PostMapping("/upload/{idInmueble}")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long idInmueble) {
		// Almacenamos el archivo utilizando el servicio de almacenamiento
		Map<String, Object> response = new HashMap<>();
		String filename = "";
		try {
			filename = storageService.store(file, idInmueble);
		} catch (RuntimeException ex) {
			response.put("Message", "Data Error On File, Error Uploading File");
			response.put("Error", ex.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Conseguimos la url del archivo
		String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		String url = ServletUriComponentsBuilder.fromHttpUrl(host) // Añadimos el host "http;//localhost:8080"
				.path("/media/file/") // Añadimos en la carpeta en la que se encuentra
										// "http;//localhost:8080/media/file"
				.path(filename) // Añadimos el nombre del archivo
								// "http;//localhost:8080/media/file/34252345345435.jpg"
				.toUriString();
		response.put("Message", "File Succesfully Stored On Server");
		response.put("URL", url);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	//@CrossOrigin(origins = { "http://localhost:4200" })
	@GetMapping("/file/{filename:.+}")
	public ResponseEntity<?> getFile(@PathVariable String filename) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Resource resource;
		try {
			resource = storageService.loadAsResource(filename);
		} catch (RuntimeException ex) {
			response.put("Message", "Data Error On File, Error Reading File");
			response.put("Error", ex.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String contentType = Files.probeContentType(resource.getFile().toPath());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(resource);
	}

}
