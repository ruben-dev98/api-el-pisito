package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import com.ipartek.springboot.backend.elpisito.models.entity.Imagen;

public interface IImagenService {

	List<Imagen> findAll();

	List<Imagen> findAllActive();

	List<Imagen> findAllInmueble(Long id);

	List<Imagen> findAllActiveInmueble(Long id);

	Imagen findById(Long id);

	Imagen save(Imagen u);

	void deleteById(Long id);

}
