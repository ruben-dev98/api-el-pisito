package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import com.ipartek.springboot.backend.elpisito.models.entity.Poblacion;

public interface IPoblacionService {

	List<Poblacion> findAll();

	List<Poblacion> findAllActive();

	Poblacion findById(Long id);

	Poblacion save(Poblacion u);

	void deleteById(Long id);

}
