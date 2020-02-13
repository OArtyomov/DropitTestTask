package com.dropit.dao;

import com.dropit.model.DeliveryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

	@Query(value = "from DeliveryEntity")
	List<DeliveryEntity> findItemsInPage(Pageable pageRequest);

}
