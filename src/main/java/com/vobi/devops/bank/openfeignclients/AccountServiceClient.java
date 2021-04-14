package com.vobi.devops.bank.openfeignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vobi.devops.bank.dto.AccountDTO;

@FeignClient(
		url = "${accounts.service.url}",
		value = "accounts-service-client"
		)
public interface AccountServiceClient {

	
	/**
	 * REST Service client for Get Mapping /{accoId}
	 * @param accoId
	 * @return
	 */
	@GetMapping(value = "/{accoId}")
	public AccountDTO findById(
			@PathVariable("accoId") String accoId
			);
	
}
