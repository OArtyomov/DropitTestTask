package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.dto.CreateAddressDTO;
import com.dropit.service.AddressService;
import org.junit.Test;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressControllerIT extends AbstractBaseIT {

	@Resource
	private AddressService addressService;


	@Test
	public void testCreateAddress() throws Exception {
		String addressAsOneLine = "Kiev, Kreshatic 26";
		CreateAddressDTO dto = createAddress(addressAsOneLine);
		assertThat(addressRepository.count()).isEqualTo(0L);
		mockMvc.perform(post("/api/v1/address")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.length()").value(2L))
				.andExpect(jsonPath("$.oneLineString").value(addressAsOneLine))
				.andExpect(jsonPath("$.id").isNotEmpty());
		assertThat(addressRepository.count()).isEqualTo(1L);
	}

	private CreateAddressDTO createAddress(String addressAsOneLine) {
		CreateAddressDTO result = new CreateAddressDTO();
		result.setAddressLine(addressAsOneLine);
		return result;
	}

	@Test
	public void testFindAddresses() throws Exception {
		String addressAsOneLine1 = "Kreshatic 26";
		String addressAsOneLine2 = "Pechersk 25";
		addressService.createAddress(createAddress(addressAsOneLine1));
		addressService.createAddress(createAddress(addressAsOneLine2));

		mockMvc.perform(get("/api/v1/address")
				.param("addressLine", "Pechersk 27")
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1L))
				.andExpect(jsonPath("$.[0].id").isNotEmpty())
				.andExpect(jsonPath("$.[0].oneLineString").value(addressAsOneLine2));
	}


}
