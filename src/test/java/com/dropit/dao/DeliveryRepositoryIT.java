package com.dropit.dao;

import com.dropit.core.AbstractBaseIT;
import com.dropit.model.DeliveryEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DeliveryRepositoryIT extends AbstractBaseIT {

	@Test
	public void testSaveDelivery() {
		DeliveryEntity deliveryEntity = new DeliveryEntity();
		deliveryEntity.setName("AA");
		deliveryRepository.save(deliveryEntity);
		assertThat(deliveryEntity.getId()).isNotNull();
	}
}