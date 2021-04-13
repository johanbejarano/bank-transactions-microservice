package com.vobi.devops.bank.service;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.vobi.devops.bank.domain.Transaction;
import com.vobi.devops.bank.domain.TransactionType;
import com.vobi.devops.bank.dto.AccountDTO;
import com.vobi.devops.bank.dto.DepositDTO;
import com.vobi.devops.bank.dto.LoginResponse;
import com.vobi.devops.bank.dto.TransactionResultDTO;
import com.vobi.devops.bank.dto.TransferDTO;
import com.vobi.devops.bank.dto.UsersDTO;
import com.vobi.devops.bank.dto.WithdrawDTO;
import com.vobi.devops.bank.entityservice.TransactionService;
import com.vobi.devops.bank.entityservice.TransactionTypeService;
import com.vobi.devops.bank.exception.ZMessManager;

import reactor.core.publisher.Mono;

@Service
@Scope("singleton")
public class BankTransactionServiceImpl implements BankTransactionService {

	private final static Double COSTO = 2000.0;

	@Autowired
	TransactionTypeService transactionTypeService;

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	WebClient accountsWebClient;
	
	@Autowired
	WebClient loginWebClient;
	
	@Autowired
	WebClient usersWebClient;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionResultDTO transfer(TransferDTO transferDTO) throws Exception {

		//Se configura el retiro
		WithdrawDTO withdrawDTO = new WithdrawDTO(transferDTO.getAccoIdOrigin(), transferDTO.getAmount(),
				transferDTO.getUserEmail());
		
		//Se realiza el retiro
		withdraw(withdrawDTO);

		//Se configura el depósito en la cuenta destino
		DepositDTO depositDTO = new DepositDTO(transferDTO.getAccoIdDestination(), transferDTO.getAmount(),
				transferDTO.getUserEmail());
		
		//Se realiza el depósito en la cuenta destino
		deposit(depositDTO);

		//Se configura el retiro del costo de la cuenta origen
		withdrawDTO = new WithdrawDTO(transferDTO.getAccoIdOrigin(), COSTO, transferDTO.getUserEmail());
		
		//Se realiza el retiro del costo de la cuenta origen
		TransactionResultDTO withdrawResult = withdraw(withdrawDTO);

		//Se configura el depósito del costo en la cuenta genérica
		depositDTO = new DepositDTO("9999-9999-9999-9999", COSTO, transferDTO.getUserEmail());
		
		//Se realiza el depósito del costo en la cuenta genérica
		deposit(depositDTO);

		//Se consulta el tipo de transacción = 3 (Transferencia)
		Optional<TransactionType> transactionType3 = transactionTypeService.findById(3);
		if (!transactionType3.isPresent()) {
			throw (new ZMessManager()).new FindingException("tipo de transacción 3");
		}
		TransactionType transactionType = transactionType3.get();

		
//		Optional<Account> accountOptional = accountService.findById(transferDTO.getAccoIdOrigin());
//		if (!accountOptional.isPresent()) {
//			throw (new ZMessManager()).new FindingException("cuenta con id " + transferDTO.getAccoIdOrigin());
//		}
//
//		Account account = accountOptional.get();
		
		//Se consulta la cuenta en el microservicio de cuentas
		AccountDTO accountDTO = getAccount(transferDTO.getAccoIdOrigin());
		if (accountDTO == null) {
			throw (new ZMessManager()).new FindingException("cuenta con id " + transferDTO.getAccoIdOrigin());
		}

//		Optional<Users> userOptional = userService.findById(transferDTO.getUserEmail());
//		if (!userOptional.isPresent()) {
//			throw (new ZMessManager()).new FindingException("Usuario con id " + transferDTO.getUserEmail());
//		}
//
//		Users user = userOptional.get();
		
		//Se consulta el usuario
		UsersDTO usersDTO = getUser(transferDTO.getUserEmail());
		if (usersDTO == null) {
			throw (new ZMessManager()).new FindingException("Usuario con id " + transferDTO.getUserEmail());
		}
		

		//Se realiza la transacción
		Transaction transaction = new Transaction();
		transaction.setAccoId(accountDTO.getAccoId());
		transaction.setAmount(transferDTO.getAmount());
		transaction.setDate(new Timestamp(System.currentTimeMillis()));
		transaction.setTranId(null);
		transaction.setTransactionType(transactionType);
		transaction.setUserEmail(usersDTO.getUserEmail());

		transactionService.save(transaction);

		//FIXME: Por ahora se retorna el balance en 0. Se corregirá cuando se implemente el retiro y el depósito
		return new TransactionResultDTO(transaction.getTranId(), 0D);

	}
	
	private AccountDTO getAccount(String accoId) throws Exception {
		
		//TODO: Ahora se quema. Se debe configurar
		String bodyString = "{"
				+ "    \"username\": \"admin\","
				+ "    \"password\": \"password\""
				+ "}";
		
		//Se autentica para obtener un token
		Mono<LoginResponse> respuestaLogin = loginWebClient.post()
				.bodyValue(bodyString)
				.retrieve()
				.bodyToMono(LoginResponse.class);
		
		LoginResponse loginResponse = respuestaLogin.block();
		
		if (loginResponse == null ) {
			throw new Exception("No se pudo autenticar con la API");
		}
		
		String token = loginResponse.getToken();
		
		
		//Se invoca la API para consultar la cuenta
		Mono<AccountDTO> respuestaConsultaCuenta = accountsWebClient.get()
			.uri("/" + accoId)
			.header(HttpHeaders.AUTHORIZATION, token)
			.retrieve()
			.bodyToMono(AccountDTO.class);
		
		return respuestaConsultaCuenta.block();
		
	}
	
	private UsersDTO getUser(String userEmail) throws Exception {
		//TODO: Ahora se quema. Se debe configurar
		String bodyString = "{"
				+ "    \"username\": \"admin\","
				+ "    \"password\": \"password\""
				+ "}";
		
		//Se autentica para obtener un token
		Mono<LoginResponse> respuestaLogin = loginWebClient.post()
				.bodyValue(bodyString)
				.retrieve()
				.bodyToMono(LoginResponse.class);
		
		LoginResponse loginResponse = respuestaLogin.block();
		
		if (loginResponse == null ) {
			throw new Exception("No se pudo autenticar con la API");
		}
		
		String token = loginResponse.getToken();
		
		
		//Se invoca la API para consultar el usuario
		Mono<UsersDTO> respuestaConsultaUsuario = usersWebClient.get()
			.uri("/" + userEmail)
			.header(HttpHeaders.AUTHORIZATION, token)
			.retrieve()
			.bodyToMono(UsersDTO.class);
		
		return respuestaConsultaUsuario.block();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionResultDTO withdraw(WithdrawDTO withdrawDTO) throws Exception {

		return null;
		
//		if (withdrawDTO == null) {
//			throw new Exception("El WithdrawDTO es nulo");
//		}
//
//		if (withdrawDTO.getAccoId() == null || withdrawDTO.getAccoId().trim().isEmpty() == true) {
//			throw new Exception("El AccoId es obligatorio");
//		}
//
//		if (withdrawDTO.getAmount() == null || withdrawDTO.getAmount() <= 0) {
//			throw new Exception("El Amount es obligatorio y debe ser mayor que cero");
//		}
//
//		if (withdrawDTO.getUserEmail() == null || withdrawDTO.getUserEmail().trim().isEmpty() == true) {
//			throw new Exception("El UserEmail es obligatorio");
//		}
//
//		if (accountService.findById(withdrawDTO.getAccoId()).isPresent() == false) {
//			throw new ZMessManager().new AccountNotFoundException(withdrawDTO.getAccoId());
//		}
//
//		Account account = accountService.findById(withdrawDTO.getAccoId()).get();
//
//		if (account.getEnable().trim().equals("N") == true) {
//			throw new ZMessManager().new AccountNotEnableException(withdrawDTO.getAccoId());
//		}
//
//		if (userService.findById(withdrawDTO.getUserEmail()).isPresent() == false) {
//			throw new ZMessManager().new UserNotFoundException(withdrawDTO.getUserEmail());
//		}
//
//		Users user = userService.findById(withdrawDTO.getUserEmail()).get();
//
//		if (user.getEnable().trim().equals("N") == true) {
//			throw new ZMessManager().new UserDisableException(withdrawDTO.getUserEmail());
//		}
//
//		TransactionType transactionType = transactionTypeService.findById(1).get();
//
//		Transaction transaction = new Transaction();
//		transaction.setAccount(account);
//		transaction.setAmount(withdrawDTO.getAmount());
//		transaction.setDate(new Timestamp(System.currentTimeMillis()));
//		transaction.setTranId(null);
//		transaction.setTransactionType(transactionType);
//		transaction.setUsers(user);
//
//		Double nuevoSaldo = account.getBalance() - withdrawDTO.getAmount();
//		account.setBalance(nuevoSaldo);
//
//		transaction = transactionService.save(transaction);
//		accountService.update(account);
//
//		TransactionResultDTO transactionResultDTO = new TransactionResultDTO(transaction.getTranId(), nuevoSaldo);
//
//		return transactionResultDTO;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TransactionResultDTO deposit(DepositDTO depositDTO) throws Exception {
		
		return null;
		
//		if (depositDTO == null) {
//			throw new Exception("El depositDTO es nulo");
//		}
//
//		if (depositDTO.getAccoId() == null || depositDTO.getAccoId().trim().isEmpty() == true) {
//			throw new Exception("El AccoId es obligatorio");
//		}
//
//		if (depositDTO.getAmount() == null || depositDTO.getAmount() < 0) {
//			throw new Exception("El Amount es obligatorio y debe ser mayor que cero");
//		}
//
//		if (depositDTO.getUserEmail() == null || depositDTO.getUserEmail().trim().isEmpty() == true) {
//			throw new Exception("El UserEmail es obligatorio");
//		}
//
//		if (accountService.findById(depositDTO.getAccoId()).isPresent() == false) {
//			throw new ZMessManager().new AccountNotFoundException(depositDTO.getAccoId());
//		}
//
//		Account account = accountService.findById(depositDTO.getAccoId()).get();
//
//		if (account.getEnable().trim().equals("N") == true) {
//			throw new ZMessManager().new AccountNotEnableException(depositDTO.getAccoId());
//		}
//
//		if (userService.findById(depositDTO.getUserEmail()).isPresent() == false) {
//			throw new ZMessManager().new UserNotFoundException(depositDTO.getUserEmail());
//		}
//
//		Users user = userService.findById(depositDTO.getUserEmail()).get();
//
//		if (user.getEnable().trim().equals("N") == true) {
//			throw new ZMessManager().new UserDisableException(depositDTO.getUserEmail());
//		}
//
//		TransactionType transactionType = transactionTypeService.findById(2).get();
//
//		Transaction transaction = new Transaction();
//		transaction.setAccount(account);
//		transaction.setAmount(depositDTO.getAmount());
//		transaction.setDate(new Timestamp(System.currentTimeMillis()));
//		transaction.setTranId(null);
//		transaction.setTransactionType(transactionType);
//		transaction.setUsers(user);
//
//		Double nuevoSaldo = account.getBalance() + depositDTO.getAmount();
//		account.setBalance(nuevoSaldo);
//
//		transaction = transactionService.save(transaction);
//		accountService.update(account);
//
//		TransactionResultDTO transactionResultDTO = new TransactionResultDTO(transaction.getTranId(), nuevoSaldo);
//
//		return transactionResultDTO;
	}

}
