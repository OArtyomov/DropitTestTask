package com.dropit.service.handlers;

import com.dropit.dao.PackageRepository;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.event.AddPackagesToDeliveryEvent;
import com.dropit.model.DeliveryEntity;
import com.dropit.service.DeliveryManager;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AddPackagesEventHandler {

	private PackageRepository packageRepository;

	private DeliveryManager deliveryManager;

	@EventListener(AddPackagesToDeliveryEvent.class)
	@Transactional
	public void handleCreateDeliveryCommand(AddPackagesToDeliveryEvent event) {
		DeliveryEntity deliveryEntity = deliveryManager.getDeliveryEntity(event.getDeliveryId());
		packageRepository.setDeliveryToPackages(event.getPackages(), deliveryEntity);
		final GETDeliveryDTO resultDTO = deliveryManager.getDeliveryAndFillPackages(deliveryEntity);
		event.setResult(resultDTO);
	}
}
