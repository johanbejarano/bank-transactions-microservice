package com.vobi.devops.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vobi.devops.bank.dto.UsersDTO;
import com.vobi.devops.bank.openfeignclients.FeignClients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceCircuitBreakerImpl implements UserServiceCiurcuitBreaker {

	@Autowired
	FeignClients feignClients;
	
	private int contadorLlamadas = 0;
	private int contadorFallos = 0;
	
	@Override
	@CircuitBreaker(name="usersService", fallbackMethod = "fallbackGetUser")
	public UsersDTO getUser(String userEmail) throws Exception {
		
		contadorLlamadas++;
		log.info("usersService - Llamados: " + contadorLlamadas);
		
		//Using OpenFeign
		return feignClients.getUser(userEmail);
		
//		//TODO: Ahora se quema. Se debe configurar
//		String bodyString = "{"
//				+ "    \"username\": \"admin\","
//				+ "    \"password\": \"password\""
//				+ "}";
//		
//		//Se autentica para obtener un token
//		Mono<LoginResponse> respuestaLogin = loginWebClient.post()
//				.bodyValue(bodyString)
//				.retrieve()
//				.bodyToMono(LoginResponse.class);
//		
//		LoginResponse loginResponse = respuestaLogin.block();
//		
//		if (loginResponse == null ) {
//			throw new Exception("No se pudo autenticar con la API");
//		}
//		
//		String token = loginResponse.getToken();
//		
//		
//		//Se invoca la API para consultar el usuario
//		Mono<UsersDTO> respuestaConsultaUsuario = usersWebClient.get()
//			.uri("/" + userEmail)
//			.header(HttpHeaders.AUTHORIZATION, token)
//			.retrieve()
//			.bodyToMono(UsersDTO.class);
//		
//		return respuestaConsultaUsuario.block();
	}
	
	public UsersDTO fallbackGetUser(String userEmail, Throwable th) throws Exception {
		contadorFallos++;
		log.info("usersService - Fallos: " + contadorFallos);
		
		throw new Exception("usersService not available");
	}
	
}
