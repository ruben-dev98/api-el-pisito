package com.ipartek.springboot.backend.elpisito.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.elpisito.models.entity.Provincia;

@Repository
public interface IProvinciaDAO extends CrudRepository<Provincia, Long> {
	List<Provincia> findByActivoTrue();
}
