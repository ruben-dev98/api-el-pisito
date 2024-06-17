package com.ipartek.springboot.backend.elpisito.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	@Bean
	@Autowired
	SecurityFilterChain securityFilterChain(HttpSecurity http, JWTValidationFilter jwtValidationFilter)
			throws Exception {
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("api/inmuebles").hasRole("ADMIN");
			auth.requestMatchers("api/inmueble").hasRole("ADMIN");
			auth.requestMatchers("api/poblaciones").hasRole("ADMIN");
			auth.requestMatchers("api/poblacion").hasRole("ADMIN");
			auth.requestMatchers("api/tipos").hasRole("ADMIN");
			auth.requestMatchers("api/tipo").hasRole("ADMIN");
			auth.requestMatchers("api/provincias").hasRole("ADMIN");
			auth.requestMatchers("api/provincia").hasRole("ADMIN");
			auth.requestMatchers("api/usuarios").hasRole("ADMIN");
			auth.anyRequest().permitAll();
		});
		http.addFilterAfter(jwtValidationFilter, BasicAuthenticationFilter.class);
		http.cors(cors -> corsConfigurationSource());
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		var config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("*")); // "*"
		config.setAllowedMethods(List.of("*")); // "*"
		config.setAllowedHeaders(List.of("*")); // "*"
		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}