package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vobi.devops.bank.dto.UsersDTO;

@FeignClient(
		url = "${users.service.url}",
		value = "users-service-client"
		)
public interface UsersServiceClient {

	@GetMapping("/{userEmail}")
	public UsersDTO getUser(
			@PathVariable("userEmail") String userEmail
			);
	
}
