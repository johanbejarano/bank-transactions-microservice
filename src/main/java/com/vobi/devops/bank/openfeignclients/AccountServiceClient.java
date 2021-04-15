package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vobi.devops.bank.dto.AccountDTO;

@FeignClient(
		value = "accounts-service"
		)
public interface AccountServiceClient {

	
	/**
	 * REST Service client for Get Mapping /{accoId}
	 * @param accoId
	 * @return
	 */
	@GetMapping(value = "/api/v1/account/{accoId}")
	public AccountDTO findById(
			@PathVariable("accoId") String accoId
			);
	
}
