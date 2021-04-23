package com.vobi.devops.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vobi.devops.bank.dto.AccountDTO;
import com.vobi.devops.bank.openfeignclients.FeignClients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceCircuitBreakerImpl implements AccountServiceCircuitBreaker{

	@Autowired
	FeignClients feignClients;
	
	private int contadorLlamadas = 0;
	private int contadorFallos = 0;
	
	@Override
	@CircuitBreaker(name="accountsService", fallbackMethod = "fallbackGetAccount")
	public AccountDTO getAccount(String accoId) throws Exception {
		
		contadorLlamadas++;
		
		log.info("accountsService - Llamados: " + contadorLlamadas);
		
		//Using OpenFeign
		return feignClients.findAcccountById(accoId);
		
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
//		//Se invoca la API para consultar la cuenta
//		Mono<AccountDTO> respuestaConsultaCuenta = accountsWebClient.get()
//			.uri("/" + accoId)
//			.header(HttpHeaders.AUTHORIZATION, token)
//			.retrieve()
//			.bodyToMono(AccountDTO.class);
//		
//		return respuestaConsultaCuenta.block();
		
	}
	
	public AccountDTO fallbackGetAccount(String accoId, Throwable th) throws Exception {
		contadorFallos++;
		
		log.info("accountsService - Fallos: " + contadorFallos);
		
		throw new Exception("accountsService not available");
	}
	
}
