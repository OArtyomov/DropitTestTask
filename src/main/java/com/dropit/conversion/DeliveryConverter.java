package com.dropit.conversion;

import com.dropit.dto.DeliveryDTO;
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
public class DeliveryConverter implements IConverter<DeliveryEntity, DeliveryDTO> {

	@Override
	public DeliveryDTO convert(DeliveryEntity source) {
		DeliveryDTO result = new DeliveryDTO();
		result.setId(source.getId());
		result.setName(source.getName());
		return result;
	}
}
