package com.vobi.devops.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.vobi.devops.bank.domain.TransactionType;
import com.vobi.devops.bank.dto.TransactionTypeDTO;

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
public interface TransactionTypeMapper {
	public TransactionTypeDTO transactionTypeToTransactionTypeDTO(TransactionType transactionType);

	public TransactionType transactionTypeDTOToTransactionType(TransactionTypeDTO transactionTypeDTO);

	public List<TransactionTypeDTO> listTransactionTypeToListTransactionTypeDTO(List<TransactionType> transactionTypes);

	public List<TransactionType> listTransactionTypeDTOToListTransactionType(
			List<TransactionTypeDTO> transactionTypeDTOs);
}
