package com.vobi.devops.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.devops.bank.dto.DepositDTO;
import com.vobi.devops.bank.dto.TransactionResultDTO;
import com.vobi.devops.bank.dto.TransferDTO;
import com.vobi.devops.bank.dto.WithdrawDTO;
import com.vobi.devops.bank.service.BankTransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "*")
public class BankTransactionController {

	@Autowired
	BankTransactionService bankTransactionService;

	@PostMapping("/transfer")
	public ResponseEntity<TransactionResultDTO> transfer(@Valid @RequestBody TransferDTO transferDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.transfer(transferDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/withdraw")
	public ResponseEntity<TransactionResultDTO> withdraw(@Valid @RequestBody WithdrawDTO withdrawDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.withdraw(withdrawDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

	@PostMapping("/deposit")
	public ResponseEntity<TransactionResultDTO> deposit(@Valid @RequestBody DepositDTO depositDTO) throws Exception {

		TransactionResultDTO transactionResultDTO = bankTransactionService.deposit(depositDTO);
		return ResponseEntity.ok().body(transactionResultDTO);

	}

}
