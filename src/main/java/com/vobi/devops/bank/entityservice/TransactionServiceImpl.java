package com.vobi.devops.bank.entityservice;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.devops.bank.domain.Transaction;
import com.vobi.devops.bank.exception.ZMessManager;
import com.vobi.devops.bank.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@Scope("singleton")
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private Validator validator;

	@Override
	public void validate(Transaction transaction) throws ConstraintViolationException {
		Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(transaction);

		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return transactionRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transaction> findAll() {
		log.debug("finding all Transaction instances");

		return transactionRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction save(Transaction entity) throws Exception {
		log.debug("saving Transaction instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Transaction");
		}

		validate(entity);

//		if (transactionRepository.existsById(entity.getTranId())) {
//			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
//		}

		return transactionRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Transaction entity) throws Exception {
		log.debug("deleting Transaction instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Transaction");
		}

		if (entity.getTranId() == null) {
			throw new ZMessManager().new EmptyFieldException("tranId");
		}

		if (transactionRepository.existsById(entity.getTranId()) == false) {
			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
		}

		transactionRepository.deleteById(entity.getTranId());
		log.debug("delete Transaction successful");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting Transaction instance");

		if (id == null) {
			throw new ZMessManager().new EmptyFieldException("tranId");
		}

		Optional<Transaction> optionalTransaction = transactionRepository.findById(id);

		if (optionalTransaction.isPresent()) {
			delete(optionalTransaction.get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Transaction update(Transaction entity) throws Exception {
		log.debug("updating Transaction instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Transaction");
		}

		validate(entity);

		if (transactionRepository.existsById(entity.getTranId()) == false) {
			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
		}

		return transactionRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Transaction> findById(Integer tranId) {
		log.debug("getting Transaction instance");

		return transactionRepository.findById(tranId);
	}
}
