package com.vobi.devops.bank.dto;

public class TransferDTO {

	private String accoIdOrigin;
	private String accoIdDestination;
	private Double amount;
	private String userEmail;

	public TransferDTO(String accoIdOrigin, String accoIdDestination, Double amount, String userEmail) {
		super();
		this.accoIdOrigin = accoIdOrigin;
		this.accoIdDestination = accoIdDestination;
		this.amount = amount;
		this.userEmail = userEmail;
	}

	public TransferDTO() {
		super();
	}

	public String getAccoIdOrigin() {
		return accoIdOrigin;
	}

	public void setAccoIdOrigin(String accoIdOrigin) {
		this.accoIdOrigin = accoIdOrigin;
	}

	public String getAccoIdDestination() {
		return accoIdDestination;
	}

	public void setAccoIdDestination(String accoIdDestination) {
		this.accoIdDestination = accoIdDestination;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
