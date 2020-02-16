package com.dropit.service;

import com.dropit.conversion.DeliveryConverter;
import com.dropit.conversion.PackageConverter;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.exceptions.DeliveryNotFoundException;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DeliveryManager {

	private DeliveryRepository deliveryRepository;

	private PackageRepository packageRepository;

	private DeliveryConverter deliveryConverter;

	private PackageConverter packageConverter;

	public DeliveryEntity getDeliveryEntity(Long deliveryId) {
		final Optional<DeliveryEntity> byId = deliveryRepository.findById(deliveryId);
		if (!byId.isPresent()) {
			throw new DeliveryNotFoundException(deliveryId);
		}
		return byId.get();
	}

	public GETDeliveryDTO getDeliveryAndFillPackages(DeliveryEntity entity) {
		final List<PackageEntity> packagesForDeliveries = packageRepository.findPackagesForDeliveries(singletonList(entity));
		return convertDelivery(entity, packagesForDeliveries);
	}

	public GETDeliveryDTO convertDelivery(DeliveryEntity item, List<PackageEntity> packageEntities) {
		GETDeliveryDTO result = deliveryConverter.convert(item);
		result.setPackages(packageConverter.convertAll(packageEntities));
		return result;
	}
}
