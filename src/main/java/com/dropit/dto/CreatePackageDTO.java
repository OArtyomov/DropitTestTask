package com.dropit.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreatePackageDTO {

	@NotEmpty
	private String tag;
}
