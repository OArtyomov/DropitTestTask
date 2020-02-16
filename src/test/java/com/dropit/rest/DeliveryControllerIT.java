package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETAddressDTO;
import com.dropit.model.DeliveryEntity;
import com.dropit.model.PackageEntity;
import com.dropit.service.AddressService;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeliveryControllerIT extends AbstractBaseIT {

	@Autowired
	private AddressService addressService;

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
	public void testGetAllDeliveriesAndQueriesCount() throws Exception {
		List<DeliveryEntity> entities = range(1, 11)
				.mapToObj(item -> buildEntity("Item " + item))
				.collect(toList());
		deliveryRepository.saveAll(entities);

		for (DeliveryEntity entity : entities) {
			final List<PackageEntity> packageEntities = range(0, 20).mapToObj(index ->
					buildPackage("Tag " + index, entity))
					.collect(toList());
			packageRepository.saveAll(packageEntities);
		}

		SQLStatementCountValidator.reset();
		mockMvc.perform(get("/api/v1/delivery"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(10))
				.andExpect(jsonPath("$.[0].name").value("Item 1"))
				.andExpect(jsonPath("$.[1].name").value("Item 10"))
				.andExpect(jsonPath("$.[2].name").value("Item 2"))
				.andExpect(jsonPath("$.[3].name").value("Item 3"))
				.andExpect(jsonPath("$.[4].name").value("Item 4"))
				.andExpect(jsonPath("$.[5].name").value("Item 5"))
				.andExpect(jsonPath("$.[6].name").value("Item 6"))
				.andExpect(jsonPath("$.[7].name").value("Item 7"))
				.andExpect(jsonPath("$.[8].name").value("Item 8"))
				.andExpect(jsonPath("$.[9].name").value("Item 9"));
		//1 query for selected deliveries, 1 query for select packages in delivery
		assertSelectCount(2);
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
	public void testCreateDeliveryWhenValidationIsOk() throws Exception {
		String deliveryName = "AA";
		String addressLine = "Kiev, UA";
		final GETAddressDTO address = addressService.createAddress(createAddress(addressLine));
		CreateDeliveryDTO dto = new CreateDeliveryDTO();
		dto.setName(deliveryName);
		dto.setAddressId(address.getId());

		MvcResult mvcResult = mockMvc.perform(post("/api/v1/delivery")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(request().asyncStarted())
				.andDo(MockMvcResultHandlers.log())
				.andReturn();

		mockMvc.perform(asyncDispatch(mvcResult))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(deliveryName))
				.andExpect(jsonPath("$.id").isNotEmpty());
	}

	@Test
	public void testCreateDeliveryWhenValidationIsFailed() throws Exception {
		String deliveryName = null;
		String addressLine = "Kiev, UA";
		final GETAddressDTO address = addressService.createAddress(createAddress(addressLine));
		CreateDeliveryDTO dto = new CreateDeliveryDTO();
		dto.setName(deliveryName);
		dto.setAddressId(address.getId());
		mockMvc.perform(post("/api/v1/delivery")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(1L))
				.andExpect(jsonPath("$.[0].fieldName").value("name"))
				.andExpect(jsonPath("$.[0].message").value("must not be empty"));
	}

}
