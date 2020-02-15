package com.dropit.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateDeliveryDTO {

	@NotEmpty
	private String name;
}
