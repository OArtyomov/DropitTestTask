package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.model.DeliveryEntity;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeliveryControllerIT extends AbstractBaseIT {

	@Test
	public void testGetAllDeliveriesWithDefaultPagingValuesAndDefaultSortingValues() throws Exception {
		List<DeliveryEntity> entities = range(1, 21)
				.mapToObj(item -> buildEntity("Item " + item))
				.collect(toList());

		deliveryRepository.saveAll(entities);
		mockMvc.perform(get("/api/v1/delivery"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(10))
				.andExpect(jsonPath("$.[0].name").value("Item 1"))
				.andExpect(jsonPath("$.[1].name").value("Item 10"))
				.andExpect(jsonPath("$.[2].name").value("Item 11"))
				.andExpect(jsonPath("$.[3].name").value("Item 12"))
				.andExpect(jsonPath("$.[4].name").value("Item 13"))
				.andExpect(jsonPath("$.[5].name").value("Item 14"))
				.andExpect(jsonPath("$.[6].name").value("Item 15"))
				.andExpect(jsonPath("$.[7].name").value("Item 16"))
				.andExpect(jsonPath("$.[8].name").value("Item 17"))
				.andExpect(jsonPath("$.[9].name").value("Item 18"));
	}

	@Test
	public void testGetAllDeliveriesWithPagingValuesAndSortingValues() throws Exception {
		List<DeliveryEntity> entities = range(1, 21)
				.mapToObj(item -> buildEntity("Item " + item))
				.collect(toList());

		deliveryRepository.saveAll(entities);
		mockMvc.perform(get("/api/v1/delivery")
				.param("page", "0")
				.param("size", "3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$.[0].name").value("Item 1"))
				.andExpect(jsonPath("$.[1].name").value("Item 10"))
				.andExpect(jsonPath("$.[2].name").value("Item 11"));
	}

	@Test
	public void testGetDeliveryByIdWhenDeliveryPresent() throws Exception {
		DeliveryEntity deliveryEntity = buildEntity("Item 1");
		deliveryRepository.save(deliveryEntity);
		mockMvc.perform(get("/api/v1/delivery/" + deliveryEntity.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$.name").value("Item 1"))
				.andExpect(jsonPath("$.id").value(deliveryEntity.getId()));
	}

	@Test
	public void testGetDeliveryByIdWhenDeliveryIsNotAvailable() throws Exception {
		Long notAvailableIdOfDelivery = 800L;
		mockMvc.perform(get("/api/v1/delivery/" + notAvailableIdOfDelivery))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$.message").value("Delivery with id 800 not found"));
	}

	@Test
	public void testCreateDeliveryWhenValidationIsOk() throws Exception {
		String deliveryName = "AA";
		CreateDeliveryDTO dto = new CreateDeliveryDTO();
		dto.setName(deliveryName);
		mockMvc.perform(post("/api/v1/delivery")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(deliveryName))
				.andExpect(jsonPath("$.id").isNotEmpty());
	}

	@Test
	public void testCreateDeliveryWhenValidationIsFailed() throws Exception {
		String deliveryName = null;
		CreateDeliveryDTO dto = new CreateDeliveryDTO();
		dto.setName(deliveryName);
		mockMvc.perform(post("/api/v1/delivery")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(1L))
				.andExpect(jsonPath("$.[0].fieldName").value("name"))
				.andExpect(jsonPath("$.[0].message").value("must not be empty"));
	}

	private DeliveryEntity buildEntity(String deliveryName) {
		DeliveryEntity deliveryEntity = new DeliveryEntity();
		deliveryEntity.setName(deliveryName);
		return deliveryEntity;
	}

}
