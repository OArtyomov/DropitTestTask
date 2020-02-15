package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.dto.CreateAddressDTO;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressControllerIT extends AbstractBaseIT {

	@Test
	public void testCreateAddress() throws Exception {
		String addressAsOneLine = "Kiev, Kreshatic 26";
		CreateAddressDTO dto = new CreateAddressDTO();
		dto.setAddressLine(addressAsOneLine);
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
}
