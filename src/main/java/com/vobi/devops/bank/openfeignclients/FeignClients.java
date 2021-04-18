package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vobi.devops.bank.dto.AccountDTO;
import com.vobi.devops.bank.dto.LoginRequest;
import com.vobi.devops.bank.dto.LoginResponse;
import com.vobi.devops.bank.dto.UsersDTO;

@FeignClient(
		value = "api-gateway"
		)
public interface FeignClients {

	/**
	 * REST Service client for Get Mapping /{accoId}
	 * @param accoId
	 * @return
	 */
	@GetMapping(value = "/accounts-service/api/v1/account/{accoId}")
	public AccountDTO findAcccountById(
			@PathVariable("accoId") String accoId
			);
	
	@GetMapping("/users-service/api/v1/users/{userEmail}")
	public UsersDTO getUser(
			@PathVariable("userEmail") String userEmail
			);
	
	@PostMapping("/users-service/login")
	public LoginResponse login (@RequestBody LoginRequest loginRequest);
	
}
