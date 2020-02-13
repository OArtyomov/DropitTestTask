package com.dropit.rest;

import com.dropit.core.AbstractBaseIT;
import com.dropit.dto.CreatePackageDTO;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PackageControllerIT extends AbstractBaseIT {

	@Test
	public void testCreatePackage() throws Exception {
		String tag = "hotel";
		CreatePackageDTO dto = new CreatePackageDTO();
		dto.setTag(tag);
		assertThat(packageRepository.count()).isEqualTo(0L);
		mockMvc.perform(post("/api/v1/package")
				.content(objectMapper.writeValueAsString(dto))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.length()").value(2L))
				.andExpect(jsonPath("$.tag").value(tag))
				.andExpect(jsonPath("$.id").isNotEmpty());
		assertThat(packageRepository.count()).isEqualTo(1L);
	}
}
