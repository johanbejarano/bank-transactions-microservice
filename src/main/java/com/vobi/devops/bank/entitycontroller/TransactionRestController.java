package com.vobi.devops.bank.entitycontroller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.devops.bank.domain.Transaction;
import com.vobi.devops.bank.dto.TransactionDTO;
import com.vobi.devops.bank.entityservice.TransactionService;
import com.vobi.devops.bank.mapper.TransactionMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@RestController
@RequestMapping("/api/v1/transaction")
@Slf4j
public class TransactionRestController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionMapper transactionMapper;

	@GetMapping(value = "/{tranId}")
	public ResponseEntity<?> findById(@PathVariable("tranId") Integer tranId) throws Exception {
		log.debug("Request to findById() Transaction");

		Optional<Transaction> optionalTransaction = transactionService.findById(tranId);

		Transaction transaction = (optionalTransaction.isPresent() == true) ? optionalTransaction.get() : null;

		return ResponseEntity.ok().body(transactionMapper.transactionToTransactionDTO(transaction));
	}

	@GetMapping()
	public ResponseEntity<?> findAll() throws Exception {
		log.debug("Request to findAll() Transaction");

		return ResponseEntity.ok()
				.body(transactionMapper.listTransactionToListTransactionDTO(transactionService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody TransactionDTO transactionDTO) throws Exception {
		log.debug("Request to save Transaction: {}", transactionDTO);

		Transaction transaction = transactionMapper.transactionDTOToTransaction(transactionDTO);
		transaction = transactionService.save(transaction);

		return ResponseEntity.ok().body(transactionMapper.transactionToTransactionDTO(transaction));
	}

	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody TransactionDTO transactionDTO) throws Exception {
		log.debug("Request to update Transaction: {}", transactionDTO);

		Transaction transaction = transactionMapper.transactionDTOToTransaction(transactionDTO);
		transaction = transactionService.update(transaction);

		return ResponseEntity.ok().body(transactionMapper.transactionToTransactionDTO(transaction));
	}

	@DeleteMapping(value = "/{tranId}")
	public ResponseEntity<?> delete(@PathVariable("tranId") Integer tranId) throws Exception {
		log.debug("Request to delete Transaction");

		transactionService.deleteById(tranId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(transactionService.count());
	}
}
