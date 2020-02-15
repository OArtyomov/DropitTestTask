package com.dropit.conversion;

import com.dropit.conversion.core.IConverter;
import com.dropit.dto.GETAddressDTO;
import com.dropit.model.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AddressConverter implements IConverter<AddressEntity, GETAddressDTO> {
	@Override
	public GETAddressDTO convert(AddressEntity source) {
		GETAddressDTO result = new GETAddressDTO();
		result.setId(source.getId());
		result.setOneLineString(source.getSpecification());
		return result;
	}
}
