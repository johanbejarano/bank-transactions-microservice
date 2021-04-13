package com.vobi.devops.bank.security;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
public class Constants {

	// Spring Security
	/*
	public static final String LOGIN_URL = "/login";
	public static final String ACTUATOR_URL = "/actuator/**";
	public static final String SWAGGER_V2_URL = "/v2/**";
	public static final String SWAGGER_UI_URL = "/swagger-ui/**";
	*/
	
	public static final String[] SECURITY_ARRAY_URLS = new String[]
			{"/login",
			 "/actuator/**",
			 "/v2/**",
			 "/swagger-resources/**",
			 "/swagger-ui.html",
			 "/swagger-ui/**",
			 "/webjars/**"			 
			 };
		
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT
	public static final String ISSUER_INFO = "https://zathuracode.org";
	public static final String SUPER_SECRET_KEY = "z4tur4c0dv92020isth3b3stcodeg3n3ratorintheworldofj4v4v0rtex2020";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
