package com.dropit.event;

import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETDeliveryDTO;
import org.springframework.context.ApplicationEvent;


public class CreateDeliveryEvent extends ApplicationEvent {

	private final CreateDeliveryDTO source;

	private GETDeliveryDTO result;

	public CreateDeliveryEvent(CreateDeliveryDTO createDeliveryDTO) {
		super((createDeliveryDTO));
		this.source = createDeliveryDTO;
	}

	public GETDeliveryDTO getResult() {
		return result;
	}

	public void setResult(GETDeliveryDTO result) {
		this.result = result;
	}

	public CreateDeliveryDTO getSource() {
		return source;
	}
}
