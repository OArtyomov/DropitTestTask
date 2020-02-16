package com.dropit.event;

import com.dropit.dto.GETDeliveryDTO;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class AddPackagesToDeliveryEvent extends ApplicationEvent {

	private final Long deliveryId;

	private final List<Long> packages;

	private GETDeliveryDTO result;

	public AddPackagesToDeliveryEvent(Long deliveryId, List<Long> packages) {
		super(packages);
		this.deliveryId = deliveryId;
		this.packages = packages;
	}

	public void setResult(GETDeliveryDTO result) {
		this.result = result;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public List<Long> getPackages() {
		return packages;
	}

	public GETDeliveryDTO getResult() {
		return result;
	}
}
