package com.dropit.conversion;

import com.dropit.conversion.core.IConverter;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.model.DeliveryEntity;
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
public class DeliveryConverter implements IConverter<DeliveryEntity, GETDeliveryDTO> {

	@Override
	public GETDeliveryDTO convert(DeliveryEntity source) {
		GETDeliveryDTO result = new GETDeliveryDTO();
		result.setId(source.getId());
		result.setName(source.getName());
		return result;
	}
}
