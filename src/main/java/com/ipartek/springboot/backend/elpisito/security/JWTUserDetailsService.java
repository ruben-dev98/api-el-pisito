package com.ipartek.springboot.backend.elpisito.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.elpisito.models.dao.IUsuarioDAO;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	// Tenemos anotado un objeto de la clase(Interface) UserDetailsService (por polimorfismo)
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return usuarioDAO.findOneByEmail(username).map(u -> {
			var authorites = List.of(new SimpleGrantedAuthority(u.getRol()));
			return new User(u.getEmail(), u.getPassword(), authorites);
		}).orElseThrow(
				() -> new UsernameNotFoundException("Usuario con el nombre de usuario " + username + "no ha sido encontrado o no existe en nuestra base de datos")
				);
		
	}
	
}
