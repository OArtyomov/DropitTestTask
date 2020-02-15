package com.dropit.dao;

import com.dropit.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	@Query(nativeQuery = true, value = "update address set specification_tsv = to_tsvector('english', specification) where id = ?1")
	@Modifying
	@Transactional
	void updateDateForIndex(Long id);
}
