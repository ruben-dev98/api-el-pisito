package com.ipartek.springboot.backend.elpisito.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ipartek.springboot.backend.elpisito.models.dao.IUsuarioDAO;
import com.ipartek.springboot.backend.elpisito.models.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	public static final long JWT_TOKEN_VALIDITY = 1200;
	public static final String JWT_SECRET = "VFHJDFSHJDFGSJHAKFDVKJHASVDJHVSAJGDVJGASCVDJGVCASJDGCSJADGJHKASGDJHASGDJHASG";

	@Autowired
	private IUsuarioDAO usuarioDAO;

	//////////////////////////////////////////////////////

	// MÉTODOS PARA SACAR INFORMACION DEL TOKEN

	/////////////////////////////////////////////////////

	private Claims getAllClaimsForToken(String token) {
		final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

	}

	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsForToken(token);
		return claimsResolver.apply(claims);
	}

	private Date getExpirationDateFromToken(String token) {
		return getClaimsFromToken(token, Claims::getExpiration/* == (c) -> c.getExpiration() */);
	}

	private boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	public String getUserNameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject/* == (c) -> c.getSubject() */);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userNameFromUserDetails = userDetails.getUsername();
		final String userNameFromJWT = getUserNameFromToken(token);
		return userNameFromJWT.equals(userNameFromUserDetails) && !isTokenExpired(token);
	}

	//////////////////////////////////////////////////////

	// MÉTODOS PARA CREAR EL TOKEN

	/////////////////////////////////////////////////////

	private String getToken(Map<String, Object> claims, String subject) {
		final Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
		return Jwts.builder().setClaims(claims) // Claims Del Token
				.setSubject(subject) // Contenido Token
				.setIssuedAt(new Date(System.currentTimeMillis())) // Fecha Token Relaizado
				.setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * 1000))) // Token Caduca
				.signWith(key) // Firmamos el key con la clave secreta
				.compact(); // El builder a String
	}

	public String generateToken(UserDetails userDetails) {
		Usuario u = usuarioDAO.findOneByEmail(userDetails.getUsername()).map(user -> user)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario con el email "
						+ userDetails.getUsername() + " no ha sido encontrado en nuestra base de datos"));
		final Map<String, Object> claims = new HashMap<>();
		claims.put("ROLES", userDetails.getAuthorities().toString());
		claims.put("USER", u.getUser());
		claims.put("ID", u.getId());
		return getToken(claims, userDetails.getUsername());
	}

}