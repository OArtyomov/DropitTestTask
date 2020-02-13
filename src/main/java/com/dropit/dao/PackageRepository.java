package com.dropit.dao;

import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

	@Query("update PackageEntity  set delivery = :deliveryEntity where ((id in (:ids)) and (delivery is null))")
	@Modifying(flushAutomatically = true)
	void setDeliveryToPackages(@Param("ids") List<Long> ids, @Param("deliveryEntity") DeliveryEntity deliveryEntity);

	@Query("from PackageEntity where delivery in (:deliveries)")
	List<PackageEntity> findPackagesForDeliveries(@Param("deliveries") List<DeliveryEntity> deliveries);
}
