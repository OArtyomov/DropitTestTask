package com.dropit.rest;

import com.dropit.dto.CreateAddressDTO;
import com.dropit.dto.GETAddressDTO;
import com.dropit.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.dropit.utils.Constants.ADDRESS_BASE_URI;
import static com.dropit.utils.Constants.COMMON_SWAGGER_TAG;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = COMMON_SWAGGER_TAG)
@RestController
@RequestMapping(ADDRESS_BASE_URI)
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AddressController {

	private AddressService addressService;

	@ApiOperation(value = "Get addresses by line.", notes = "The API returns list of addresses")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public List<GETAddressDTO> findAddress(@RequestParam String addressLine){
     	return addressService.findAddressesByLine(addressLine);
	 }

	@ApiOperation(value = "Create address.", notes = "The API creates address")
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<GETAddressDTO> createAddress(@RequestBody @Valid CreateAddressDTO dto) {
		return new ResponseEntity<>(addressService.createAddress(dto), CREATED);
	}

}