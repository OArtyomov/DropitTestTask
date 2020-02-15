package com.dropit.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTest {

	@InjectMocks
	private AddressService addressService;

	@Test
	public void testConvertToTextForFullSearch() {
		assertThat(addressService.convertToTextForFullSearch("Ukraine Kiev")).isEqualTo("Ukraine | Kiev");
	}
}