package com.vobi.devops.bank.openfeignclients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.vobi.devops.bank.dto.LoginRequest;
import com.vobi.devops.bank.dto.LoginResponse;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientsInterceptor implements RequestInterceptor {

	@Autowired
	FeignClients feignClient;
	
	@Value("${login.service.username}")
	String username;
	
	@Value("${login.service.password}")
	String password;
	
	@Override
	public void apply(RequestTemplate template) {
		
		//Se autentica el servicio, para todas las invocaciones EXCEPTO la del mismo /login
		String url = template.url();
		
		if (url.indexOf("/login") >= 0) {
			return;
		}
		
		String bearerToken = null;
		
		//Se obtiene el token, autenticándose con la API
		//Se consume el cliente de login
		
		LoginRequest loginRequest = new LoginRequest(username, password);
		LoginResponse loginResponse = feignClient.login(loginRequest);
		
		if (loginResponse == null) {
			return;
		}
		bearerToken = loginResponse.getToken();
		
		//Se adiciona el bearer token en la cabecera de todas las peticiones OpenFeign
		template.header(HttpHeaders.AUTHORIZATION, bearerToken);
		
	}

}
