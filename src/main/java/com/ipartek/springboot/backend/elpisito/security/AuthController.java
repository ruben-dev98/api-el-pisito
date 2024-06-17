package com.ipartek.springboot.backend.elpisito.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JWTUserDetailsService jwtUserDetailsService;
	private final JWTService jwtService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> postToken(@RequestBody JWTRequest request) {
		authenticate(request);
		final var userDetails = jwtUserDetailsService.loadUserByUsername(request.getUsername());
		final String token = jwtService.generateToken(userDetails);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer ".concat(token));
		
		return ResponseEntity.ok() //Devuelve un Body Builder
				.headers(headers) //Devuelve lo mismo
				.body(new JWTResponse(token)); //Devuelve un ResponseEntity
	}
	
	private void authenticate(JWTRequest request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException bce) {
			throw new RuntimeException(bce.getMessage());
		} catch(DisabledException de) {
			throw new RuntimeException(de.getMessage());
		} catch(AuthenticationException ae) {
			throw new RuntimeException(ae.getMessage());
		}
	}
}
