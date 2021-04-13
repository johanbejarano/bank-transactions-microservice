package com.vobi.devops.bank.service;

import com.vobi.devops.bank.dto.DepositDTO;
import com.vobi.devops.bank.dto.TransactionResultDTO;
import com.vobi.devops.bank.dto.TransferDTO;
import com.vobi.devops.bank.dto.WithdrawDTO;

public interface BankTransactionService {

	TransactionResultDTO withdraw(WithdrawDTO withdrawDTO) throws Exception;

	TransactionResultDTO deposit(DepositDTO depositDTO) throws Exception;

	TransactionResultDTO transfer(TransferDTO transferDTO) throws Exception;

}
