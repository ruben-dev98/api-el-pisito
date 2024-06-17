package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.elpisito.models.dao.IImagenDAO;
import com.ipartek.springboot.backend.elpisito.models.entity.Imagen;
import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;

@Service
public class ImagenServiceImpl implements IImagenService {

	@Autowired
	private IImagenDAO imagenDAO;

	@Override
	public List<Imagen> findAll() {
		return (List<Imagen>) imagenDAO.findAll();
	}

	@Override
	public List<Imagen> findAllActive() {
		return imagenDAO.findByActivoTrue();
	}

	@Override
	public List<Imagen> findAllInmueble(Long id) {
		Inmueble im = new Inmueble();
		im.setId(id);
		return imagenDAO.findByInmueble(im);
	}

	@Override
	public List<Imagen> findAllActiveInmueble(Long id) {
		Inmueble im = new Inmueble();
		im.setId(id);
		return imagenDAO.findByInmuebleAndActivoTrue(im);
	}

	@Override
	public Imagen findById(Long id) {
		return imagenDAO.findById(id).orElse(null);
	}

	@Override
	public Imagen save(Imagen u) {
		return imagenDAO.save(u);
	}

	@Override
	public void deleteById(Long id) {
		imagenDAO.deleteById(id);
	}

}
