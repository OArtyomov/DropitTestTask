package com.dropit.rest;


import com.dropit.dto.GETDeliveryDTO;
import com.dropit.service.DeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dropit.utils.Constants.COMMON_SWAGGER_TAG;
import static com.dropit.utils.Constants.DELIVERY_ENTRY_BASE_URI;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = COMMON_SWAGGER_TAG)
@RestController
@RequestMapping(DELIVERY_ENTRY_BASE_URI)
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryEntryController {

	private DeliveryService deliveryService;


	@ApiOperation(value = "Get delivery by id.", notes = "The API returns delivery by id")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<GETDeliveryDTO> getDeliveryById(@PathVariable("deliveryId") Long deliveryId) {
		return new ResponseEntity<>(deliveryService.getDelivery(deliveryId), OK);
	}

	@ApiOperation(value = "Append package to delivery", notes = "The API append packages to delivery")
	@PostMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<GETDeliveryDTO> appendPackagesToDelivery(@PathVariable("deliveryId") Long deliveryId,
																   @RequestBody List<Long> packages) {
		return new ResponseEntity<>(deliveryService.appendPackagesToDelivery(deliveryId, packages), OK);
	}

}
