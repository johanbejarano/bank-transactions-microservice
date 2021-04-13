package com.vobi.devops.bank.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class TransactionTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull
	@NotEmpty
	@Size(max = 1)
	private String enable;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	private String name;
	@NotNull
	private Integer trtyId;
}
