package com.customer.management.controller;

import com.customer.management.model.CustomerRequest;
import com.customer.management.model.CustomerResponse;
import com.customer.management.model.ErrorMessage;
import com.customer.management.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/customer")
@Validated
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Operation(summary = "Create a new customer")
	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> addCustomer(@RequestBody @Validated CustomerRequest request) {
		logger.info("Creating customer for CustomerAddRequest : {}", request);
		CustomerResponse response = customerService.addCustomer(request);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);

	}

	@Operation(summary = "Get customer by ID")
	@GetMapping(value = "/id/{customerId}", produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long customerId) {
		logger.info("START get : getCustomerById {}", customerId);
		CustomerResponse response = customerService.findCustomerById(customerId);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Get customer by name")
	@GetMapping(value = "/name", produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> getCustomerByName(@RequestParam(name = "name") @NotBlank @Size(min = 2, max = 100) String name) {
		logger.info("START get : getCustomerByName {}", name);
		CustomerResponse response = customerService.findCustomerByName(name);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Get customer by email")
	@GetMapping(value = "/email", produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam String email) {
		logger.info("START get : getCustomerById {}", email);
		CustomerResponse response = customerService.findCustomerByEmail(email);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Update customer")
	@PutMapping(value = "/{customerId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long customerId,
			@RequestBody @Validated CustomerRequest request) {
		logger.info("Updating customer with ID: {}", customerId);

		CustomerResponse response = customerService.updateCustomer(customerId, request);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Delete customer")
	@DeleteMapping(value = "/{customerId}", produces = APPLICATION_JSON_VALUE)
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "204", description = "No Content", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "405", description = "Method Not Allowed", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessage.class)))
	public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long customerId) {
		logger.info("START get : deleteCustomer {}", customerId);
		CustomerResponse response = customerService.deleteCustomer(customerId);
		logger.info("END get : response {}", response);
		return ResponseEntity.ok(response);
	}
}
