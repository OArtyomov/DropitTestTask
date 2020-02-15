package com.dropit.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateAddressDTO {

	@NotEmpty
	private String addressLine;
}
