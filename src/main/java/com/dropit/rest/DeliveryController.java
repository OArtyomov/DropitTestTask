package com.dropit.rest;


import com.dropit.dto.DeliveryDTO;
import com.dropit.service.DeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dropit.utils.Constants.COMMON_SWAGGER_TAG;
import static com.dropit.utils.Constants.DELIVERY_BASE_URI;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = COMMON_SWAGGER_TAG)
@RestController
@RequestMapping(DELIVERY_BASE_URI)
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeliveryController {

	private DeliveryService deliveryService;

	@ApiOperation(value = "Get all deliveries.", notes = "The API returns list of deliveries",
			nickname = "CDSGroups_getGroupByGroupIdUsingGET",
			extensions = {
					@Extension(properties = {
							@ExtensionProperty(name = "x-category", value = "CDSGroups"),
							@ExtensionProperty(name = "x-operationName", value = "getGroupByGroupIdUsingGET"),
							@ExtensionProperty(name = "x-tag", value = "CDSGroups API: Object Groups")
					})
			})
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DeliveryDTO>>
	getAllDeliveries(@PageableDefault(sort = {"name"}) Pageable pageRequest) {
		return new ResponseEntity<>(deliveryService.getAllDeliveries(pageRequest), OK);
	}

	@ApiOperation(value = "Get all deliveries.", notes = "The API returns list of deliveries",
			nickname = "CDSGroups_getGroupByGroupIdUsingGET",
			extensions = {
					@Extension(properties = {
							@ExtensionProperty(name = "x-category", value = "CDSGroups"),
							@ExtensionProperty(name = "x-operationName", value = "getGroupByGroupIdUsingGET"),
							@ExtensionProperty(name = "x-tag", value = "CDSGroups API: Object Groups")
					})
			})
	@GetMapping(produces = APPLICATION_JSON_VALUE, value = "/{deliveryId}")
	public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable("deliveryId") Long deliveryId) {
		return new ResponseEntity<>(deliveryService.getDelivery(deliveryId), OK);
	}

}
