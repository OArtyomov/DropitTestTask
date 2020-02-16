package com.dropit.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AddPackagesToDeliverySource {

	private Long deliveryId;

	private List<Long> packages;
}
