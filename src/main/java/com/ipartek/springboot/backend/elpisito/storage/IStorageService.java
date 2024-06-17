package com.ipartek.springboot.backend.elpisito.storage;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	// Metodo auxiliar para preparar todo lo necesario
	// para la subida de archivos
	void init() throws IOException;

	// Con este metodo amacenamos fisicamente
	// el archivo en la carpeta de destino(mediafiles: aplication-properties)
	String store(MultipartFile file, Long idInmueble);

	Resource loadAsResource(String fileName);

}
