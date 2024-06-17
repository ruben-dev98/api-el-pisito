package com.ipartek.springboot.backend.elpisito.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.elpisito.models.entity.Imagen;
import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;

@Repository
public interface IImagenDAO extends CrudRepository<Imagen, Long> {
	List<Imagen> findByActivoTrue();

	List<Imagen> findByInmueble(Inmueble im);

	List<Imagen> findByInmuebleAndActivoTrue(Inmueble im);

}
