package com.dropit.rest;


import com.dropit.dto.CreatePackageDTO;
import com.dropit.dto.GETPackageDTO;
import com.dropit.service.PackageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.dropit.utils.Constants.COMMON_SWAGGER_TAG;
import static com.dropit.utils.Constants.PACKAGE_BASE_URI;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = COMMON_SWAGGER_TAG)
@RestController
@RequestMapping(PACKAGE_BASE_URI)
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PackageController {

	private PackageService packageService;

	@ApiOperation(value = "Create package.", notes = "The API create package")
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<GETPackageDTO> createPackage(@RequestBody @Valid CreatePackageDTO dto) {
		return new ResponseEntity<>(packageService.createPackage(dto), CREATED);
	}


}
