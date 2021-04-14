package com.vobi.devops.bank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@SpringBootApplication
@EnableFeignClients("com.vobi.devops.bank.openfeignclients")
public class SpringBootRunner {
	
//	@Value("${accounts.service.url}")
//	private String accountsServiceUrl;
//	
//	@Value("${login.service.url}")
//	private String loginServiceUrl;
//	
//	@Value("${users.service.url}")
//	private String usersServiceUrl;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRunner.class, args);
	}
	
//	@Bean
//	public WebClient accountsWebClient() {
//		return WebClient.builder()
//			.baseUrl(accountsServiceUrl)
//			.build();
//	}
//	
//	@Bean
//	public WebClient loginWebClient() {
//		return WebClient.builder()
//			.baseUrl(loginServiceUrl)
//			.build();
//	}
//	
//	@Bean
//	public WebClient usersWebClient() {
//		return WebClient.builder()
//			.baseUrl(usersServiceUrl)
//			.build();
//	}
}
