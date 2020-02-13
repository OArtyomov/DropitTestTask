package com.dropit.service;

import com.dropit.conversion.DeliveryConverter;
import com.dropit.dao.DeliveryRepository;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.DeliveryDTO;
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

	private DeliveryConverter deliveryConverter;

	@Transactional
	public List<DeliveryDTO> getAllDeliveries(Pageable pageRequest) {
		return deliveryConverter.convertAll(deliveryRepository.findItemsInPage(pageRequest));
	}

	@Transactional
	public DeliveryDTO getDelivery(Long deliveryId) {
		final Optional<DeliveryEntity> entityById = deliveryRepository.findById(deliveryId);
		if (entityById.isPresent()) {
			return deliveryConverter.convert(entityById.get());
		}
		throw new DeliveryNotFoundException(deliveryId);
	}

	public DeliveryDTO createDelivery(CreateDeliveryDTO dto) {
		DeliveryEntity entity = new DeliveryEntity();
		entity.setName(dto.getName());
		deliveryRepository.save(entity);
		return deliveryConverter.convert(entity);
	}
}
