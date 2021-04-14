package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vobi.devops.bank.dto.LoginRequest;
import com.vobi.devops.bank.dto.LoginResponse;

@FeignClient(
		url = "${login.service.url}",
		path = "/",
		value = "login-service-client"
		)
public interface LoginServiceClient {

	@PostMapping
	public LoginResponse login (@RequestBody LoginRequest loginRequest);
	
}
