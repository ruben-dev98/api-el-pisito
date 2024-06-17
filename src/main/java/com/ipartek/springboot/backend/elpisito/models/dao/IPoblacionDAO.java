package com.ipartek.springboot.backend.elpisito.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.elpisito.models.entity.Poblacion;

@Repository
public interface IPoblacionDAO extends CrudRepository<Poblacion, Long> {
	List<Poblacion> findByActivoTrue();
}
