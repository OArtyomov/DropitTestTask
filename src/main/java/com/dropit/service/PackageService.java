package com.dropit.service;

import com.dropit.conversion.PackageConverter;
import com.dropit.dao.PackageRepository;
import com.dropit.dto.CreatePackageDTO;
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
public class PackageService {

	private PackageRepository packageRepository;

	private PackageConverter packageConverter;

	public GETPackageDTO createPackage(CreatePackageDTO dto) {
		PackageEntity packageEntity = new PackageEntity();
		packageEntity.setTag(dto.getTag());
		packageRepository.save(packageEntity);
		return packageConverter.convert(packageEntity);
	}
}
