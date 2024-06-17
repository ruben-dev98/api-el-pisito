package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;

public interface IInmuebleService {

	List<Inmueble> findAll();

	List<Inmueble> findAllActive();

	List<Inmueble> findAllPortada();

	List<Inmueble> findByPoblacionAndTipo(Long idPob, Long idTipo);

	List<Inmueble> findByPoblacionAndTipoAndOperacion(Long idPob, Long idTipo, String op);

	List<Inmueble> findByPoblacionAndTipoAndOperacionAndActivoTrue(Long pob, Long tipo, String op);

	List<Inmueble> findAllFinder(Long idPob, Long idTipo, String op);

	Inmueble findById(Long id);

	Inmueble save(Inmueble u);

	void deleteById(Long id);

}
