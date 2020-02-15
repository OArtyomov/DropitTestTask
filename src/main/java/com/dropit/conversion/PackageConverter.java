package com.dropit.conversion;

import com.dropit.conversion.core.IConverter;
import com.dropit.dto.GETPackageDTO;
import com.dropit.model.PackageEntity;
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
public class PackageConverter implements IConverter<PackageEntity, GETPackageDTO> {
	@Override
	public GETPackageDTO convert(PackageEntity source) {
		GETPackageDTO result = new GETPackageDTO();
		result.setId(source.getId());
		result.setTag(source.getTag());
		return result;
	}
}
