package com.dropit.service;

import com.dropit.conversion.DeliveryConverter;
import com.dropit.conversion.PackageConverter;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.event.CreateDeliveryEvent;
import com.dropit.exceptions.DeliveryNotFoundException;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DeliveryService {

	private DeliveryRepository deliveryRepository;

	private PackageRepository packageRepository;

	private DeliveryConverter deliveryConverter;

	private PackageConverter packageConverter;

	private ApplicationContext applicationContext;

	@Transactional
	public List<GETDeliveryDTO> getAllDeliveries(Pageable pageRequest) {
		final List<DeliveryEntity> deliveries = deliveryRepository.findItemsInPage(pageRequest);
		if (!isEmpty(deliveries)) {
			return getDeliveriesAndFillPackages(deliveries);
		}
		return emptyList();
	}

	private List<GETDeliveryDTO> getDeliveriesAndFillPackages(List<DeliveryEntity> deliveries) {
		final List<PackageEntity> packagesForDeliveries = packageRepository.findPackagesForDeliveries(deliveries);
		Map<DeliveryEntity, List<PackageEntity>> packagesForDelivery = packagesForDeliveries
				.stream()
				.collect(groupingBy(PackageEntity::getDelivery));

		return deliveries
				.stream()
				.map(item -> convertDelivery(item, packagesForDelivery.get(item)))
				.collect(toList());
	}

	private GETDeliveryDTO getDeliveryAndFillPackages(DeliveryEntity entity) {
		final List<PackageEntity> packagesForDeliveries = packageRepository.findPackagesForDeliveries(singletonList(entity));
		return convertDelivery(entity, packagesForDeliveries);
	}


	private GETDeliveryDTO convertDelivery(DeliveryEntity item, List<PackageEntity> packageEntities) {
		GETDeliveryDTO result = deliveryConverter.convert(item);
		result.setPackages(packageConverter.convertAll(packageEntities));
		return result;
	}

	@Transactional
	public GETDeliveryDTO getDelivery(Long deliveryId) {
		return getDeliveryAndFillPackages(getDeliveryEntity(deliveryId));
	}

	private DeliveryEntity getDeliveryEntity(Long deliveryId) {
		final Optional<DeliveryEntity> byId = deliveryRepository.findById(deliveryId);
		if (!byId.isPresent()) {
			throw new DeliveryNotFoundException(deliveryId);
		}
		return byId.get();
	}

	public GETDeliveryDTO createDelivery(CreateDeliveryDTO dto) {
		final CreateDeliveryEvent event = new CreateDeliveryEvent(dto);
		applicationContext.publishEvent(event);
		return event.getResult();
	}

	@Transactional
	public GETDeliveryDTO appendPackagesToDelivery(Long deliveryId, List<Long> packages) {
		DeliveryEntity deliveryEntity = getDeliveryEntity(deliveryId);
		packageRepository.setDeliveryToPackages(packages, deliveryEntity);
		return getDeliveryAndFillPackages(deliveryEntity);
	}
}
