package com.dropit.service;

import com.dropit.dao.DeliveryRepository;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.event.AddPackagesToDeliveryEvent;
import com.dropit.event.AddPackagesToDeliverySource;
import com.dropit.event.CreateDeliveryEvent;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.dropit.utils.Constants.ASYNC_TASK_EXECUTOR_BEAN_NAME;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.concurrent.CompletableFuture.completedFuture;
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

	private ApplicationContext applicationContext;

	private DeliveryManager deliveryManager;

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
				.map(item -> deliveryManager.convertDelivery(item, packagesForDelivery.get(item)))
				.collect(toList());
	}

	public GETDeliveryDTO getDeliveryAndFillPackages(DeliveryEntity entity) {
		final List<PackageEntity> packagesForDeliveries = packageRepository.findPackagesForDeliveries(singletonList(entity));
		return deliveryManager.convertDelivery(entity, packagesForDeliveries);
	}

	@Transactional
	public GETDeliveryDTO getDelivery(Long deliveryId) {
		return getDeliveryAndFillPackages(deliveryManager.getDeliveryEntity(deliveryId));
	}

	@Async(value = ASYNC_TASK_EXECUTOR_BEAN_NAME)
	public CompletableFuture<GETDeliveryDTO> createDelivery(CreateDeliveryDTO dto) {
		final CreateDeliveryEvent event = new CreateDeliveryEvent(dto);
		applicationContext.publishEvent(event);
		return completedFuture(event.getResult());
	}

	@Async(value = ASYNC_TASK_EXECUTOR_BEAN_NAME)
	public CompletableFuture<GETDeliveryDTO> appendPackagesToDelivery(Long deliveryId, List<Long> packages) {
		AddPackagesToDeliveryEvent event = new AddPackagesToDeliveryEvent(new AddPackagesToDeliverySource(deliveryId, packages));
		applicationContext.publishEvent(event);
		return completedFuture(event.getResult());
	}
}
