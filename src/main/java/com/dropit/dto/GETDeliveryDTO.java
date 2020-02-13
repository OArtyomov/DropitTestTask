package com.dropit.dto;

import lombok.Data;

import java.util.List;

@Data
public class GETDeliveryDTO {

	private Long id;

	private String name;

	private List<GETPackageDTO> packages;
}
