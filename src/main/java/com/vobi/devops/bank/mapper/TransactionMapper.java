package com.vobi.devops.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.devops.bank.domain.Transaction;
import com.vobi.devops.bank.dto.TransactionDTO;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 *         Mapper Build with MapStruct https://mapstruct.org/ MapStruct is a
 *         code generator that greatly simplifies the implementation of mappings
 *         between Java bean type based on a convention over configuration
 *         approach.
 */
@Mapper
public interface TransactionMapper {
	@Mapping(source = "userEmail", target = "userEmail_Users")
	@Mapping(source = "transactionType.trtyId", target = "trtyId_TransactionType")
	@Mapping(source = "accoId", target = "accoId_Account")
	public TransactionDTO transactionToTransactionDTO(Transaction transaction);

	@Mapping(source = "userEmail_Users", target = "userEmail")
	@Mapping(source = "trtyId_TransactionType", target = "transactionType.trtyId")
	@Mapping(source = "accoId_Account", target = "accoId")
	public Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);

	public List<TransactionDTO> listTransactionToListTransactionDTO(List<Transaction> transactions);

	public List<Transaction> listTransactionDTOToListTransaction(List<TransactionDTO> transactionDTOs);
}
