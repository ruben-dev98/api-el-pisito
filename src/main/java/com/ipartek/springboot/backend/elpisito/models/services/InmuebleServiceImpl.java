package com.ipartek.springboot.backend.elpisito.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.elpisito.models.dao.IInmuebleDAO;
import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;
import com.ipartek.springboot.backend.elpisito.models.entity.Poblacion;
import com.ipartek.springboot.backend.elpisito.models.entity.Tipo;

@Service
public class InmuebleServiceImpl implements IInmuebleService {

	@Autowired
	private IInmuebleDAO inmuebleDAO;

	@Override
	public List<Inmueble> findAll() {
		return (List<Inmueble>) inmuebleDAO.findAll();
	}

	@Override
	public List<Inmueble> findByPoblacionAndTipo(Long idPob, Long idTipo) {
		return (List<Inmueble>) inmuebleDAO.findByPoblacionAndTipo(idPob, idTipo);
	}

	@Override
	public List<Inmueble> findByPoblacionAndTipoAndOperacion(Long idPob, Long idTipo, String op) {
		return (List<Inmueble>) inmuebleDAO.findByPoblacionAndTipoAndOperacion(idPob, idTipo, op);
	}

	@Override
	public List<Inmueble> findAllActive() {
		return inmuebleDAO.findByActivoTrue();
	}

	@Override
	public List<Inmueble> findAllPortada() {
		return inmuebleDAO.findByPortadaTrueAndActivoTrue();
	}

	@Override
	public List<Inmueble> findAllFinder(Long idPob, Long idTipo, String op) {
		return findByPoblacionAndTipoAndOperacion(idPob, idTipo, op);
	}

	@Override
	public List<Inmueble> findByPoblacionAndTipoAndOperacionAndActivoTrue(Long pob, Long tipo, String op) {
		Tipo t = new Tipo();
		Poblacion p = new Poblacion();
		t.setId(tipo);
		p.setId(pob);
		return (List<Inmueble>) inmuebleDAO.findByPoblacionAndTipoAndOperacionAndActivoTrue(p, t, op);
	}

	@Override
	public Inmueble findById(Long id) {
		return inmuebleDAO.findById(id).orElse(null);
	}

	@Override
	public Inmueble save(Inmueble u) {
		return inmuebleDAO.save(u);
	}

	@Override
	public void deleteById(Long id) {
		inmuebleDAO.deleteById(id);
	}

}
