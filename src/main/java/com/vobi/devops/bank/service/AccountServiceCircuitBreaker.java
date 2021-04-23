package com.vobi.devops.bank.service;

import com.vobi.devops.bank.dto.AccountDTO;

public interface AccountServiceCircuitBreaker {

	public AccountDTO getAccount(String accoId) throws Exception;
	
}
