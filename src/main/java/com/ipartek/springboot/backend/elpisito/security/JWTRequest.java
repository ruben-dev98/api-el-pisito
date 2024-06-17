package com.ipartek.springboot.backend.elpisito.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTRequest {
	
	private String username;
	private String password;

}
