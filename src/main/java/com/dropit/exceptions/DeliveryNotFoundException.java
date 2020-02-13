package com.dropit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeliveryNotFoundException extends RuntimeException {
	private Long id;
}
