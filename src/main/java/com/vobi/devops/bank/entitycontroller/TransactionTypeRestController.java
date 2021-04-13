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

import com.vobi.devops.bank.domain.TransactionType;
import com.vobi.devops.bank.dto.TransactionTypeDTO;
import com.vobi.devops.bank.entityservice.TransactionTypeService;
import com.vobi.devops.bank.mapper.TransactionTypeMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@RestController
@RequestMapping("/api/v1/transactionType")
@Slf4j
public class TransactionTypeRestController {
	@Autowired
	private TransactionTypeService transactionTypeService;
	@Autowired
	private TransactionTypeMapper transactionTypeMapper;

	@GetMapping(value = "/{trtyId}")
	public ResponseEntity<?> findById(@PathVariable("trtyId") Integer trtyId) throws Exception {
		log.debug("Request to findById() TransactionType");

		Optional<TransactionType> optionalTransactionType = transactionTypeService.findById(trtyId);

		TransactionType transactionType = (optionalTransactionType.isPresent() == true) ? optionalTransactionType.get()
				: null;

		return ResponseEntity.ok().body(transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionType));
	}

	@GetMapping()
	public ResponseEntity<?> findAll() throws Exception {
		log.debug("Request to findAll() TransactionType");

		return ResponseEntity.ok().body(
				transactionTypeMapper.listTransactionTypeToListTransactionTypeDTO(transactionTypeService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws Exception {
		log.debug("Request to save TransactionType: {}", transactionTypeDTO);

		TransactionType transactionType = transactionTypeMapper.transactionTypeDTOToTransactionType(transactionTypeDTO);
		transactionType = transactionTypeService.save(transactionType);

		return ResponseEntity.ok().body(transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionType));
	}

	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody TransactionTypeDTO transactionTypeDTO) throws Exception {
		log.debug("Request to update TransactionType: {}", transactionTypeDTO);

		TransactionType transactionType = transactionTypeMapper.transactionTypeDTOToTransactionType(transactionTypeDTO);
		transactionType = transactionTypeService.update(transactionType);

		return ResponseEntity.ok().body(transactionTypeMapper.transactionTypeToTransactionTypeDTO(transactionType));
	}

	@DeleteMapping(value = "/{trtyId}")
	public ResponseEntity<?> delete(@PathVariable("trtyId") Integer trtyId) throws Exception {
		log.debug("Request to delete TransactionType");

		transactionTypeService.deleteById(trtyId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(transactionTypeService.count());
	}
}
