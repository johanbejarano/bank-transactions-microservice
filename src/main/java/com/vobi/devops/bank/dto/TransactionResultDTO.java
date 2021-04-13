package com.vobi.devops.bank.dto;

public class TransactionResultDTO {

	private Integer transactionId;
	private Double balance;

	public TransactionResultDTO(Integer transactionId, Double balance) {
		super();
		this.transactionId = transactionId;
		this.balance = balance;
	}

	public TransactionResultDTO() {
		super();
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
