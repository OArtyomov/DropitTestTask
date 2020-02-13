package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeliveryEntryControllerIT extends AbstractBaseIT {

	@Test
	public void testGetDeliveryByIdWhenDeliveryPresent() throws Exception {
		DeliveryEntity deliveryEntity = buildEntity("Item 1");
		deliveryRepository.save(deliveryEntity);
		mockMvc.perform(get("/api/v1/delivery/" + deliveryEntity.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$.name").value("Item 1"))
				.andExpect(jsonPath("$.packages").isArray())
				.andExpect(jsonPath("$.packages").isEmpty())
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
	public void testAppendPackageToDelivery() throws Exception {
		DeliveryEntity deliveryEntity = buildEntity("Item 1");
		deliveryRepository.save(deliveryEntity);
		final List<PackageEntity> packageEntities = IntStream.range(1, 11).mapToObj(value -> buildPackage("Tag " + value, null)).collect(toList());
		packageRepository.saveAll(packageEntities);
		List<Long> ids = packageEntities.stream().map(entity -> entity.getId()).collect(toList());
		mockMvc.perform(post("/api/v1/delivery/" + deliveryEntity.getId())
				.content(objectMapper.writeValueAsString(ids))
				.contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name").value("Item 1"))
				.andExpect(jsonPath("$.packages.length()").value(10L))
				.andExpect(jsonPath("$.packages.[0].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[0].tag").value("Tag 1"))
				.andExpect(jsonPath("$.packages.[1].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[1].tag").value("Tag 2"))
				.andExpect(jsonPath("$.packages.[2].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[2].tag").value("Tag 3"))
				.andExpect(jsonPath("$.packages.[3].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[3].tag").value("Tag 4"))
				.andExpect(jsonPath("$.packages.[4].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[4].tag").value("Tag 5"))
				.andExpect(jsonPath("$.packages.[5].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[5].tag").value("Tag 6"))
				.andExpect(jsonPath("$.packages.[6].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[6].tag").value("Tag 7"))
				.andExpect(jsonPath("$.packages.[7].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[7].tag").value("Tag 8"))
				.andExpect(jsonPath("$.packages.[8].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[8].tag").value("Tag 9"))
				.andExpect(jsonPath("$.packages.[9].id").isNotEmpty())
				.andExpect(jsonPath("$.packages.[9].tag").value("Tag 10"));
	}

}
