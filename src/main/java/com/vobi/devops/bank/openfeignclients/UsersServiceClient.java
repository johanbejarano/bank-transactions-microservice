package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vobi.devops.bank.dto.LoginRequest;
import com.vobi.devops.bank.dto.LoginResponse;
import com.vobi.devops.bank.dto.UsersDTO;

@FeignClient(
		value = "users-service"
		)
public interface UsersServiceClient {

	@GetMapping("/api/v1/users/{userEmail}")
	public UsersDTO getUser(
			@PathVariable("userEmail") String userEmail
			);
	
	@PostMapping("/login")
	public LoginResponse login (@RequestBody LoginRequest loginRequest);
	
}
