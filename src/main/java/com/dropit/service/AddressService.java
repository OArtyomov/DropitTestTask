package com.dropit.service;

import com.dropit.conversion.AddressConverter;
import com.dropit.dao.AddressRepository;
import com.dropit.dto.CreateAddressDTO;
import com.dropit.dto.GETAddressDTO;
import com.dropit.model.AddressEntity;
import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Splitter.on;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AddressService {

	private AddressRepository addressRepository;

	private AddressConverter addressConverter;

	public List<GETAddressDTO> findAddressesByLine(String addressLine) {
		return addressConverter.convertAll(
				addressRepository.findAddresses(convertToTextFoFullSearch(addressLine)));
	}

	protected String convertToTextFoFullSearch(String addressLine) {
		return on(" ")
				.omitEmptyStrings()
				.trimResults()
				.splitToList(addressLine)
				.stream()
				.filter(item -> !StringUtils.isEmpty(item))
				.collect(Collectors.joining(" | "));
	}

	public GETAddressDTO createAddress(CreateAddressDTO dto) {
		AddressEntity entity = new AddressEntity();
		entity.setSpecification(dto.getAddressLine());
		addressRepository.save(entity);
		addressRepository.updateDateForIndex(entity.getId());
		return addressConverter.convert(entity);
	}
}
