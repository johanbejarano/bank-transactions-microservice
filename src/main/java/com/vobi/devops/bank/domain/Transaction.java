package com.vobi.devops.bank.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@Entity
@Table(name = "transaction", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "tran_id", unique = true, nullable = false)
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer tranId;
	
	
	@Column(name = "acco_id")
	@NotNull
	private String accoId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trty_id")
	@NotNull
	private TransactionType transactionType;
	
	
	@Column(name = "user_email")
	@NotNull
	private String userEmail;
	
	
	@NotNull
	@Column(name = "amount", nullable = false)
	private Double amount;
	@NotNull
	@Column(name = "date", nullable = false)
	private Date date;
}
