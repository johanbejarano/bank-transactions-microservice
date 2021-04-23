package com.vobi.devops.bank.service;

import com.vobi.devops.bank.dto.UsersDTO;

public interface UserServiceCiurcuitBreaker {

	public UsersDTO getUser(String userEmail) throws Exception;
	
}
