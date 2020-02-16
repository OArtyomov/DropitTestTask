package com.dropit.service.handlers;

import com.dropit.conversion.DeliveryConverter;
import com.dropit.dao.AddressRepository;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.event.CreateDeliveryEvent;
import com.dropit.model.AddressEntity;
import com.dropit.model.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CreateDeliveryEventHandler {

	private AddressRepository addressRepository;

	private DeliveryRepository deliveryRepository;

	private DeliveryConverter deliveryConverter;

	private AddressEntity validateCreateDeliveryCommand(CreateDeliveryDTO dto) {
		Long addressId = dto.getAddressId();
		final Optional<AddressEntity> address = addressRepository.findById(addressId);
		if (!address.isPresent()) {
			throw new IllegalArgumentException("No address with id " + addressId);
		}
		return address.get();
	}

	@EventListener(CreateDeliveryEvent.class)
	public void handleCreateDeliveryCommand(CreateDeliveryEvent event) {
		final AddressEntity address = validateCreateDeliveryCommand(event.getSource());
		CreateDeliveryDTO dto = event.getSource();
		DeliveryEntity entity = new DeliveryEntity();
		entity.setName(dto.getName());
		entity.setAddress(address);
		deliveryRepository.save(entity);
		event.setResult(deliveryConverter.convert(entity));
	}
}
