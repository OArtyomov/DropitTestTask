package com.dropit.rest;


import com.dropit.dto.CreateDeliveryDTO;
import com.dropit.dto.GETDeliveryDTO;
import com.dropit.service.DeliveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.dropit.utils.Constants.COMMON_SWAGGER_TAG;
import static com.dropit.utils.Constants.DELIVERY_BASE_URI;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
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

	@ApiOperation(value = "Get all deliveries.", notes = "The API returns list of deliveries")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GETDeliveryDTO>> getAllDeliveries(@PageableDefault(sort = {"name"}) Pageable pageRequest) {
		return new ResponseEntity<>(deliveryService.getAllDeliveries(pageRequest), OK);
	}

	@ApiOperation(value = "Get all deliveries by address.", notes = "The API returns list of deliveries for specific address")
	@GetMapping(produces = APPLICATION_JSON_VALUE, value = "/byAddress")
	public ResponseEntity<List<GETDeliveryDTO>> getAllDeliveries(@PageableDefault(sort = {"name"}) Pageable pageRequest,
																 @RequestParam  Long addressId) {
		return new ResponseEntity<>(deliveryService.getAllDeliveriesByAddress(pageRequest, addressId), OK);
	}

	@ApiOperation(value = "Create delivery.", notes = "The API create delivery")
	@PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<GETDeliveryDTO>> createDelivery(@RequestBody @Valid CreateDeliveryDTO dto) {
		final CompletableFuture<ResponseEntity<GETDeliveryDTO>> future = deliveryService.createDelivery(dto).thenApply(result -> new ResponseEntity<>(result, CREATED))
				.exceptionally(handleCreateDeliveryFailure);
		return future;
	}

	private static Function<Throwable, ResponseEntity<GETDeliveryDTO>> handleCreateDeliveryFailure = throwable -> {
		log.error("Failed to create delivery: ", throwable);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	};


}
