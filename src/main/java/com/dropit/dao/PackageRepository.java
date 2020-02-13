package com.dropit.dao;

import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {

    @Query("update PackageEntity  set deliveryEntity = :deliveryEntity where ((id in (:ids)) and (deliveryEntity is null))")
	@Modifying(flushAutomatically = true)
	void setDeliveryToPackages(@Param("ids") List<Long> ids , @Param("deliveryEntity") DeliveryEntity deliveryEntity);
}
