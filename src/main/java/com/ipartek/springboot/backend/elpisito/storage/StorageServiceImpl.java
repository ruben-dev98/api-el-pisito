package com.ipartek.springboot.backend.elpisito.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ipartek.springboot.backend.elpisito.models.dao.IImagenDAO;
import com.ipartek.springboot.backend.elpisito.models.dao.IInmuebleDAO;
import com.ipartek.springboot.backend.elpisito.models.entity.Imagen;
import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;

import jakarta.annotation.PostConstruct;

@Service
public class StorageServiceImpl implements IStorageService {

	// Este objeto nos va a permitir tener acceso
	// a todos los metodos de CrudRepository<Imagen, Long>
	@Autowired
	private IImagenDAO imagenDAO;
	@Autowired
	private IInmuebleDAO inmuebleDAO;
	// Con esta propiedad estamos indicando que la propiedad
	// definida como "media.location" en el archivo aplication-properties
	// es la ruta en la que queremos albergar los archivos subidos
	@Value("${media.location}")
	private String mediaLocation;
	// Este objeto de tipo Path representara la ruta raiz de almacenamiento
	private Path rootLocation;

	@Override
	@PostConstruct // Esta anotacion ejecuta este metodo en el momento de instanciar la clase
	public void init() throws IOException {
		// Inicializamos la ruta raiz de almacenamiento
		rootLocation = Paths.get(mediaLocation);
		Files.createDirectories(rootLocation);
	}

	@Override
	public String store(MultipartFile file, Long idInmueble) throws RuntimeException {
		// String filenameUpload = "";
		// DOS FASES:
		// PASOS 1,2,3,4 Consiste en las operaciones con el archivo fisico
		// PASO 5 Vinculacion de la BBDD con el arhico fisico
		try {
			// Verificamos si el archivo esta (file) vacio
			if (file.isEmpty()) {
				throw new RuntimeException("File Not Exist");
			}
			// 1- Vamos a conseguir el tipo de archivo. Ejemplo: ".jpg", ".pdf"
			// El metodo getContentType nos devuelve el tipo MIME
			String fileContentType = file.getContentType();
			String tipo = "." + fileContentType.substring(fileContentType.lastIndexOf("/") + 1);
			if (tipo.equals(".jpeg")) {
				tipo = ".jpg";
			}
			if (tipo.equals(".pdf")) {
				throw new RuntimeException("PDF Files Are Not Allowed");
			}
			// Si deseamos completar-matizar otros tpos de archivos no dejar subir mas
			// que archivos jpg o cualquier otro matriz de filtro debemos programarlo aqui
			// 2- Conseguimos el nombre del archivo
			String filename = String.valueOf(Calendar.getInstance().getTimeInMillis());// 2434234324234
			String filenameUpload = filename.concat(tipo);// 2434234324234.jpg
			// 3- Añadimos el String nombre del archivo (filename)
			// a la ruta prefijada del destino de almacenamiento (rootLocation)
			Path destinationFile = rootLocation.resolve(Paths.get(filenameUpload));
			// 4- Movemos fisicamente el archivo a su destino final
			// Esto es un try con recursos. Para utilizar un try con recursos es necesario
			// que las clases utilizadas dentro del parentesis implementen la interface
			// autoclosable
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
			// 5- Creamos la anotacion en la BBDD
			Imagen img = new Imagen();
			Inmueble im = inmuebleDAO.findById(idInmueble).orElse(null);
			if (im.equals(null)) {
				throw new RuntimeException("Inmueble Not Exist");
			}
			img.setNombre(filenameUpload);
			img.setInmueble(im);
			imagenDAO.save(img);
			return filenameUpload;
		} catch (IOException ioex) {
			ioex.printStackTrace();
			throw new RuntimeException("Error On File Storage");
		} // return filenameUpload;
	}

	@Override
	public Resource loadAsResource(String filename) throws RuntimeException {
		// Obtenemos el path real del archivo
		Path filePath = rootLocation.resolve(Paths.get(filename));
		try {
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException(filename + " Not Readeable File");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(filename + " Not Readeable File");
		}
	}

}
