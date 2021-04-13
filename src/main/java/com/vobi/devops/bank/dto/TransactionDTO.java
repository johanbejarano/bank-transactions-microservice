package com.vobi.devops.bank.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull
	private Double amount;
	@NotNull
	private Date date;
	@NotNull
	private Integer tranId;
	private String accoId_Account;
	private Integer trtyId_TransactionType;
	private String userEmail_Users;
}
