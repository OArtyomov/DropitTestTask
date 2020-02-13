package com.dropit.service;

import com.dropit.conversion.DeliveryConverter;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.exceptions.DeliveryNotFoundException;
import com.dropit.model.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class DeliveryService {

	private DeliveryRepository deliveryRepository;

	private PackageRepository packageRepository;

	private DeliveryConverter deliveryConverter;

	@Transactional
	public List<GETDeliveryDTO> getAllDeliveries(Pageable pageRequest) {
		return deliveryConverter.convertAll(deliveryRepository.findItemsInPage(pageRequest));
	}

	@Transactional
	public GETDeliveryDTO getDelivery(Long deliveryId) {
		return deliveryConverter.convert(getDeliveryEntity(deliveryId));
	}

	private DeliveryEntity getDeliveryEntity(Long deliveryId) {
		final Optional<DeliveryEntity> byId = deliveryRepository.findById(deliveryId);
		if (!byId.isPresent()) {
			throw new DeliveryNotFoundException(deliveryId);
		}
		return byId.get();
	}

	public GETDeliveryDTO createDelivery(CreateDeliveryDTO dto) {
		DeliveryEntity entity = new DeliveryEntity();
		entity.setName(dto.getName());
		deliveryRepository.save(entity);
		return deliveryConverter.convert(entity);
	}

	@Transactional
	public GETDeliveryDTO appendPackagesToDelivery(Long deliveryId, List<Long> packages) {
		DeliveryEntity deliveryEntity = getDeliveryEntity(deliveryId);
		packageRepository.setDeliveryToPackages(packages, deliveryEntity);
		return deliveryConverter.convert(deliveryEntity);
	}
}
