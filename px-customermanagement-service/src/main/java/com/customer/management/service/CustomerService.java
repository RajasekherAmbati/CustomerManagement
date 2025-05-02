package com.customer.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.customer.management.dto.CustomerEntity;
import com.customer.management.model.Customer;
import com.customer.management.model.CustomerRequest;
import com.customer.management.model.CustomerResponse;
import com.customer.management.repository.CustomerRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	public CustomerResponse addCustomer(CustomerRequest customerAddRequest) {
	    if (customerAddRequest == null || isNullOrEmpty(customerAddRequest.getName())) {
	        logger.error("Invalid input: customerAddRequest is null or missing fields");
	        return buildErrorResponse("Customer name is required.");
	    }
	    
	    if (isNullOrEmpty(customerAddRequest.getName())) {
	        logger.error("Invalid input: customer name is missing");
	        return buildErrorResponse("Customer name is required.");
	    }
	    
	    if (isNullOrEmpty(customerAddRequest.getEmail()) || !isValidEmail(customerAddRequest.getEmail())) {
	        logger.error("Invalid input: email is null or in an incorrect format");
	        return buildErrorResponse("Valid email is required.");
	    }

	    logger.info("Attempting to add customer with request: {}", customerAddRequest);

	    try {
	        // Check if the customer already exists by name
	        Optional<CustomerEntity> existingCustomer = customerRepository.findByNameIgnoreCase(customerAddRequest.getName());

	        if (existingCustomer.isPresent()) {
	            logger.info("Customer already exists with name: {}", existingCustomer.get().getName());
	            return convertToCustomerResponse(existingCustomer.get(), "Customer already exists with this name");
	        }

	        // Create and save the new customer
	        CustomerEntity newCustomer = new CustomerEntity();
	        newCustomer.setName(customerAddRequest.getName());
	        newCustomer.setEmail(customerAddRequest.getEmail());
	        newCustomer.setAnnualSpend(customerAddRequest.getAnnualSpend());
	        newCustomer.setLastPurchaseDate(LocalDateTime.now());

	        CustomerEntity savedCustomer = customerRepository.save(newCustomer);
	        logger.info("Successfully added new customer with ID: {}", savedCustomer.getId());

	        return convertToCustomerResponse(savedCustomer, "Customer added successfully");

	    } catch (Exception e) {
	        logger.error("Exception occurred while adding customer", e);
	        return buildErrorResponse("An error occurred while adding the customer.");
	    }
	}

	public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
	    if (customerId == null || customerRequest == null) {
	        logger.error("Invalid input: customerId or customerRequest is null");
	        return buildErrorResponse("Customer ID and request body must not be null");
	    }
	    
	    if (isNullOrEmpty(customerRequest.getName())) {
	        logger.error("Invalid input: customer name is missing");
	        return buildErrorResponse("Customer name is required.");
	    }
	    
	    if (isNullOrEmpty(customerRequest.getEmail()) || !isValidEmail(customerRequest.getEmail())) {
	        logger.error("Invalid input: email is null or in an incorrect format");
	        return buildErrorResponse("Valid email is required.");
	    }

	    logger.info("Attempting to update customer with ID: {}", customerId);

	    try {
	        return customerRepository.findById(customerId)
	                .map(existingCustomer -> {
	                    applyCustomerUpdates(existingCustomer, customerRequest);
	                    CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
	                    logger.info("Successfully updated customer with ID: {}", customerId);
	                    return convertToCustomerResponse(updatedCustomer, "Customer updated successfully");
	                })
	                .orElseGet(() -> {
	                    logger.warn("Customer not found with ID: {}", customerId);
	                    return buildErrorResponse("Customer not found with ID: " + customerId);
	                });
	    } catch (Exception e) {
	        logger.error("Error occurred while updating customer with ID: {}", customerId, e);
	        return buildErrorResponse("An error occurred while updating the customer");
	    }
	}

	private void applyCustomerUpdates(CustomerEntity customer, CustomerRequest request) {
		customer.setName(request.getName());
		customer.setEmail(request.getEmail());
		customer.setAnnualSpend(request.getAnnualSpend());
		customer.setLastPurchaseDate(LocalDateTime.now());
	}

	public CustomerResponse deleteCustomer(Long customerId) {
	    logger.info("Attempting to delete customer with ID: {}", customerId);

	    if (customerId == null) {
	        logger.error("Customer ID is null");
	        return buildErrorResponse("Customer ID cannot be null");
	    }

	    try {
	        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(customerId);

	        if (optionalCustomer.isPresent()) {
	            CustomerEntity customerEntity = optionalCustomer.get();
	            customerRepository.delete(customerEntity);
	            logger.info("Successfully deleted customer with ID: {}", customerId);

	            // Return a response with customer info and success message
	            return buildErrorResponse("Customer deleted successfully");
	        } else {
	            logger.warn("Customer not found with ID: {}", customerId);
	            return buildErrorResponse("Customer not found");
	        }

	    } catch (Exception e) {
	        logger.error("Error occurred while deleting customer with ID: {}", customerId, e);
	        return buildErrorResponse("An error occurred while deleting the customer");
	    }
	}

	public CustomerResponse findCustomerById(Long customerId) {
		if (customerId == null) {
			logger.error("Customer ID is null");
			return buildErrorResponse("Customer ID cannot be null");
		}

		logger.info("Attempting to find customer with ID: {}", customerId);

		try {
			return customerRepository.findById(customerId).map(customer -> {
				logger.info("Successfully found customer with ID: {}", customerId);
				return convertToCustomerResponse(customer, "Customer retrieved successfully");
			}).orElseGet(() -> {
				logger.warn("Customer not found with ID: {}", customerId);
				return buildErrorResponse("Customer not found with ID: " + customerId);
			});
		} catch (Exception e) {
			logger.error("Error occurred while finding customer with ID: {}", customerId, e);
			return buildErrorResponse("An error occurred while retrieving the customer");
		}
	}

	public CustomerResponse findCustomerByName(String customerName) {
		if (isNullOrEmpty(customerName)) {
			logger.error("Customer name is null or empty");
			return buildErrorResponse("Customer name cannot be null or empty");
		}

		logger.info("Attempting to find customer with name: {}", customerName);

		try {
			return customerRepository.findByNameIgnoreCase(customerName).map(customer -> {
				logger.info("Successfully found customer with name: {}", customerName);
				return convertToCustomerResponse(customer, "Customer retrieved successfully");
			}).orElseGet(() -> {
				logger.warn("Customer not found with name: {}", customerName);
				return buildErrorResponse("Customer not found with name: " + customerName);
			});
		} catch (Exception e) {
			logger.error("Error occurred while finding customer with name: {}", customerName, e);
			return buildErrorResponse("An error occurred while retrieving the customer");
		}
	}

	public CustomerResponse findCustomerByEmail(String customerEmail) {
		if (isNullOrEmpty(customerEmail)) {
			logger.error("Customer email is null or empty");
			return buildErrorResponse("Customer email cannot be null or empty");
		}

		logger.info("Attempting to find customer with email: {}", customerEmail);

		try {
			return customerRepository.findByEmail(customerEmail).map(customer -> {
				logger.info("Successfully found customer with email: {}", customerEmail);
				return convertToCustomerResponse(customer, "Customer retrieved successfully");
			}).orElseGet(() -> {
				logger.warn("Customer not found with email: {}", customerEmail);
				return buildErrorResponse("Customer not found with email: " + customerEmail);
			});

		} catch (Exception e) {
			logger.error("Error occurred while finding customer with email: {}", customerEmail, e);
			return buildErrorResponse("An error occurred while retrieving the customer");
		}
	}

	private boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}
	
	private boolean isValidEmail(String email) {
	    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
	    return email != null && email.matches(emailRegex);
	}

	private CustomerResponse convertToCustomerResponse(CustomerEntity entity, String message) {
		Customer customer = new Customer();
		customer.setId(entity.getId());
		customer.setName(entity.getName());
		customer.setEmail(entity.getEmail());
		customer.setAnnualSpend(entity.getAnnualSpend());
		customer.setLastPurchaseDate(entity.getLastPurchaseDate());
		customer.setTier(determineCustomerTier(entity));

		CustomerResponse response = new CustomerResponse();
		response.setCustomer(customer);
		response.setMessage(message);
		return response;
	}

	private CustomerResponse buildErrorResponse(String message) {
		CustomerResponse response = new CustomerResponse();
		response.setCustomer(null);
		response.setMessage(message);
		return response;
	}

	private String determineCustomerTier(CustomerEntity customer) {
		if (customer == null || customer.getAnnualSpend() == null || customer.getLastPurchaseDate() == null) {
			return "";
		}

		double spend = customer.getAnnualSpend();
		LocalDateTime lastPurchase = customer.getLastPurchaseDate();
		LocalDateTime now = LocalDateTime.now();

		if (spend >= 1000 && spend < 10000 && lastPurchase.isAfter(now.minusMonths(12))) {
			return "Gold";
		}

		if (spend >= 10000 && lastPurchase.isAfter(now.minusMonths(6))) {
			return "Platinum";
		}

		return "Silver";
	}

}
