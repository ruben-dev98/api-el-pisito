package com.ipartek.springboot.backend.elpisito.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.springboot.backend.elpisito.models.entity.Usuario;

@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
	List<Usuario> findByActivoTrue();
	Optional<Usuario> findOneByEmail(String email);
}
