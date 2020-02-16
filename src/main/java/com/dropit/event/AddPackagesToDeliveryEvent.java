package com.dropit.event;

import com.dropit.dto.GETDeliveryDTO;
import org.springframework.context.ApplicationEvent;

public class AddPackagesToDeliveryEvent extends ApplicationEvent {

	private AddPackagesToDeliverySource source;

	private GETDeliveryDTO result;

	public AddPackagesToDeliveryEvent(AddPackagesToDeliverySource source) {
		super(source);
		this.source = source;
	}

	public void setResult(GETDeliveryDTO result) {
		this.result = result;
	}

	@Override
	public AddPackagesToDeliverySource getSource() {
		return source;
	}

	public GETDeliveryDTO getResult() {
		return result;
	}
}
