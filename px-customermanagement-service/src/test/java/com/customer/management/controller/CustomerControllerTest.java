package com.customer.management.controller;

import com.customer.management.model.Customer;
import com.customer.management.model.CustomerRequest;
import com.customer.management.model.CustomerResponse;
import com.customer.management.model.ErrorMessage;
import com.customer.management.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;
    private Long customerId = 1L;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john.doe@example.com");

        // Initialize the Customer object with sample data
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setAnnualSpend(1000.00);
        customer.setLastPurchaseDate(LocalDateTime.now());
        customer.setTier("Gold");

        // Initialize the CustomerResponse object with the Customer and message
        customerResponse = new CustomerResponse(customer, "Customer created successfully");
    }
    
    @Test
    void testAddCustomer_GoldTier() {
        // Gold tier: Spend between 1000 and 10000, purchased within last 12 months
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setAnnualSpend(5000.00); // Gold tier spend
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(6)); // Purchased within last 12 months
        customer.setTier("Gold");

        CustomerResponse customerResponse = new CustomerResponse(customer, "Customer created successfully");
        when(customerService.addCustomer(customerRequest)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.addCustomer(customerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).addCustomer(customerRequest);
    }

    @Test
    void testAddCustomer_PlatinumTier() {
        // Platinum tier: Spend >= 10000, purchased within last 6 months
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jane Doe");
        customer.setEmail("jane.doe@example.com");
        customer.setAnnualSpend(15000.00); // Platinum tier spend
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(3)); // Purchased within last 6 months
        customer.setTier("Platinum");

        CustomerResponse customerResponse = new CustomerResponse(customer, "Customer created successfully");
        when(customerService.addCustomer(customerRequest)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.addCustomer(customerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Platinum", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).addCustomer(customerRequest);
    }

    @Test
    void testAddCustomer_SilverTier() {
        // Silver tier: Spend < 1000 or purchased older than 12 months
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jim Doe");
        customer.setEmail("jim.doe@example.com");
        customer.setAnnualSpend(500.00); // Silver tier spend
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(18)); // Purchased more than 12 months ago
        customer.setTier("Silver");

        CustomerResponse customerResponse = new CustomerResponse(customer, "Customer created successfully");
        when(customerService.addCustomer(customerRequest)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.addCustomer(customerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Silver", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).addCustomer(customerRequest);
    }

    // Test case to validate "Gold" tier logic
    @Test
    void testGetCustomerById_GoldTier() {
        when(customerService.findCustomerById(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).findCustomerById(customerId);
    }

    // Test case to validate "Platinum" tier logic
    @Test
    void testGetCustomerById_PlatinumTier() {
        // Platinum tier: Spend >= 10000, purchased within last 6 months
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jane Doe");
        customer.setEmail("jane.doe@example.com");
        customer.setAnnualSpend(15000.00); // Platinum tier spend
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(3)); // Purchased within last 6 months
        customer.setTier("Platinum");

        CustomerResponse customerResponse = new CustomerResponse(customer, "Customer found successfully");
        when(customerService.findCustomerById(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Platinum", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).findCustomerById(customerId);
    }

    // Test case to validate "Silver" tier logic
    @Test
    void testGetCustomerById_SilverTier() {
        // Silver tier: Spend < 1000 or purchased older than 12 months
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Jim Doe");
        customer.setEmail("jim.doe@example.com");
        customer.setAnnualSpend(500.00); // Silver tier spend
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(18)); // Purchased more than 12 months ago
        customer.setTier("Silver");

        CustomerResponse customerResponse = new CustomerResponse(customer, "Customer found successfully");
        when(customerService.findCustomerById(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Silver", response.getBody().getCustomer().getTier());
        verify(customerService, times(1)).findCustomerById(customerId);
    }


    @Test
    void testAddCustomer() {
        when(customerService.addCustomer(customerRequest)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.addCustomer(customerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).addCustomer(customerRequest);
    }

    @Test
    void testGetCustomerById() {
        when(customerService.findCustomerById(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerById(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).findCustomerById(customerId);
    }

    @Test
    void testGetCustomerByName() {
        when(customerService.findCustomerByName("John Doe")).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerByName("John Doe");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).findCustomerByName("John Doe");
    }

    @Test
    void testGetCustomerByEmail() {
        when(customerService.findCustomerByEmail("john.doe@example.com")).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.getCustomerByEmail("john.doe@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).findCustomerByEmail("john.doe@example.com");
    }

    @Test
    void testUpdateCustomer() {
        when(customerService.updateCustomer(customerId, customerRequest)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.updateCustomer(customerId, customerRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).updateCustomer(customerId, customerRequest);
    }

    @Test
    void testDeleteCustomer() {
        when(customerService.deleteCustomer(customerId)).thenReturn(customerResponse);

        ResponseEntity<CustomerResponse> response = customerController.deleteCustomer(customerId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("John Doe", response.getBody().getCustomer().getName());
        assertEquals("john.doe@example.com", response.getBody().getCustomer().getEmail());
        assertEquals(1000.00, response.getBody().getCustomer().getAnnualSpend());
        assertEquals("Gold", response.getBody().getCustomer().getTier());
        assertNotNull(response.getBody().getCustomer().getLastPurchaseDate());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
    
  

}