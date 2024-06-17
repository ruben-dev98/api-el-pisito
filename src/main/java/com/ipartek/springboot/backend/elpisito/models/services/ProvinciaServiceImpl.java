package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.elpisito.models.dao.IProvinciaDAO;
import com.ipartek.springboot.backend.elpisito.models.entity.Provincia;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	private IProvinciaDAO provinciaDAO;

	@Override
	public List<Provincia> findAll() {
		return (List<Provincia>) provinciaDAO.findAll();
	}

	@Override
	public List<Provincia> findAllActive() {
		return provinciaDAO.findByActivoTrue();
	}

	@Override
	public Provincia findById(Long id) {
		return provinciaDAO.findById(id).orElse(null);
	}

	@Override
	public Provincia save(Provincia u) {
		return provinciaDAO.save(u);
	}

	@Override
	public void deleteById(Long id) {
		provinciaDAO.deleteById(id);
	}

}
