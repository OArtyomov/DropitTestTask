package com.dropit.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateDeliveryDTO {

	@NotEmpty
	private String name;

	@NotNull
	private Long addressId;
}
