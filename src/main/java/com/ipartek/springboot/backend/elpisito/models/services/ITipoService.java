package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import com.ipartek.springboot.backend.elpisito.models.entity.Tipo;

public interface ITipoService {

	List<Tipo> findAll();

	List<Tipo> findAllActive();

	Tipo findById(Long id);

	Tipo save(Tipo u);

	void deleteById(Long id);

}
