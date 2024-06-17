package com.ipartek.springboot.backend.elpisito.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.elpisito.models.entity.Inmueble;
import com.ipartek.springboot.backend.elpisito.models.entity.Poblacion;
import com.ipartek.springboot.backend.elpisito.models.entity.Tipo;

@Repository
public interface IInmuebleDAO extends CrudRepository<Inmueble, Long> {

	@Query(value = "Select * from inmuebles where poblacion=?1 and tipo=?2", nativeQuery = true)
	List<Inmueble> findByPoblacionAndTipo(Long pob, Long tipo);

	@Query(value = "Select * from inmuebles where poblacion=?1 and tipo=?2 and operacion=?3 and activo=1", nativeQuery = true)
	List<Inmueble> findByPoblacionAndTipoAndOperacion(Long pob, Long tipo, String operacion);

	List<Inmueble> findByPoblacionAndTipoAndOperacionAndActivoTrue(Poblacion pob, Tipo tipo, String operacion);

	List<Inmueble> findByActivoTrue();

	List<Inmueble> findByPortadaTrueAndActivoTrue();

}
