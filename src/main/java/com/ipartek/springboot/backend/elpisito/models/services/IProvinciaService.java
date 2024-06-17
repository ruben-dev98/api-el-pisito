package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import com.ipartek.springboot.backend.elpisito.models.entity.Provincia;

public interface IProvinciaService {

	List<Provincia> findAll();

	List<Provincia> findAllActive();

	Provincia findById(Long id);

	Provincia save(Provincia u);

	void deleteById(Long id);

}
