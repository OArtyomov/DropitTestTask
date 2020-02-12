package com.dropit.dao;

import com.dropit.model.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<DeliveryEntity, Long> {
}
